package com.pos.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pos.model.Sale;
import com.pos.model.SaleItem;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pos.commons.Response;
import com.pos.pojos.XSale;
import com.pos.service.SaleService;

/**
 * Created by rrampall on 22/12/17.
 */
@RestController
//Allowing from all for now
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/v1")
public class SalesController {

  @Autowired
  private SaleService saleService;

  @GetMapping(value="sales")
  public List<XSale> getAllSales(){
    return saleService.getAllSaleIds();
  }

  @GetMapping(value="sales/{saleId}")
  public @ResponseBody
  XSale fetchSale(@PathVariable(value="saleId") String saleId ){
    return saleService.fetchSale(saleId);
  }

  @PutMapping(value="sales")
  public @ResponseBody
  ResponseEntity<HttpStatus> editSale(@RequestBody XSale xSale){
    saleService.editSale(xSale);
    return new Response<HttpStatus>().noContent().build();
  }

  @PostMapping (value="sales")
  public void addSale(@RequestBody XSale xSale){
    Sale sale = saleService.addSale(xSale);
   printBill(sale);
  }

  private void printBill(Sale sale){
    try (PDDocument doc = new PDDocument()) {

      PDPage myPage = new PDPage();
      doc.addPage(myPage);

      try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

        cont.beginText();

        cont.setFont(PDType1Font.TIMES_ROMAN, 12);
        cont.setLeading(14.5f);

        cont.newLineAtOffset(25, 700);
        String line1 = "MORE SUPER MARKET";
        cont.showText(line1);

        cont.newLine();

        String line2 = "Invoice # " + sale.getInvoiceNumber();
        cont.showText(line2);
        cont.newLine();

        String line3 = "Customer : " + sale.getCustomer().getName() + "/" + sale.getCustomer().getMobileNumber();
        cont.showText(line3);
        cont.newLine();


        String timeLine = "Time : " + sale.getSaleTime();
        cont.showText(timeLine);
        cont.newLine();


        for(SaleItem saleItem : sale.getSaleItems()) {

          String qty = saleItem.getQuantity() == Math.floor(saleItem.getQuantity()) ?
                  String.valueOf(saleItem.getQuantity().intValue()) : String.valueOf(saleItem.getQuantity()) ;
          cont.showText(saleItem.getItem().getName() + "    " + saleItem.getItem().getPrice() +
                    "(" + qty + ")" + "    " + saleItem.getItem().getPrice());
          cont.newLine();
        }

//        String line4 = "eventually forming two opposing military "
//                + "alliances: the Allies and the Axis.";
        //cont.showText(line4);

        cont.newLine();

        cont.endText();

        String[][] content = {{"a","b", "1"},
                {"c","d", "2"},
                {"e","f", "3"},
                {"g","h", "4"},
                {"i","j", "5"}} ;


        drawTable(myPage, cont, 700, 100, content);
        cont.close();


      }

      doc.save("src/main/resources/wwii.pdf");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  private   void drawTable(PDPage page, PDPageContentStream contentStream,
                               float y, float margin,
                               String[][] content) throws IOException {
    final int rows = content.length;
    final int cols = content[0].length;
    final float rowHeight = 20f;
    final float tableWidth = page.getMediaBox().getWidth() - margin - margin;
    final float tableHeight = rowHeight * rows;
    final float colWidth = tableWidth/(float)cols;
    final float cellMargin=5f;

    //draw the rows
    float nexty = y ;
    for (int i = 0; i <= rows; i++) {
      contentStream.drawLine(margin, nexty, margin+tableWidth, nexty);
      nexty-= rowHeight;
    }

    //draw the columns
    float nextx = margin;
    for (int i = 0; i <= cols; i++) {
      contentStream.drawLine(nextx, y, nextx, y-tableHeight);
      nextx += colWidth;
    }

    //now add the text
    contentStream.setFont( PDType1Font.HELVETICA_BOLD , 12 );

    float textx = margin+cellMargin;
    float texty = y-15;
    for(int i = 0; i < content.length; i++){
      for(int j = 0 ; j < content[i].length; j++){
        String text = content[i][j];
        contentStream.beginText();
        contentStream.moveTextPositionByAmount(textx,texty);
        contentStream.drawString(text);
        contentStream.endText();
        textx += colWidth;
      }
      texty-=rowHeight;
      textx = margin+cellMargin;
    }
  }

  @GetMapping(value="filteredSales")
  public @ResponseBody Map<Date, String> getFilteredSales(@RequestParam(value="searchPattern")
    String date) {
    return saleService.getSaleDateAndIdMapping(date);
  }
}
