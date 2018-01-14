import React, { PropTypes } from 'react';
import SearchInput from 'grommet/components/SearchInput';
import DateTime from 'grommet/components/DateTime';
import Select from 'grommet/components/Select';
import Table from 'grommet/components/Table';
import TableRow from 'grommet/components/TableRow';
import Button from 'grommet/components/Button';
import Add from 'grommet/components/icons/base/Add';
import Edit from 'grommet/components/icons/base/Edit';
import Close from 'grommet/components/icons/base/Close';
import Value from 'grommet/components/Value';
import Label from 'grommet/components/Label';
import Layer from 'grommet/components/Layer';
import Form from 'grommet/components/Form';
import Header from 'grommet/components/Header';
import Footer from 'grommet/components/Footer';
import Heading from 'grommet/components/Heading';
import FormField from 'grommet/components/FormField';
import TextInput from 'grommet/components/TextInput';
import Status from 'grommet/components/icons/Status';


class Procurements extends React.Component {

   constructor(){
     super();
     this.addProduct=this.addProduct.bind(this);
     this.valueEntered=this.valueEntered.bind(this);
     this.productDetailsAdded=this.productDetailsAdded.bind(this);
     this.closeLayer=this.closeLayer.bind(this);
     this.fieldEntered=this.fieldEntered.bind(this);
     this.populateOptions=this.populateOptions.bind(this);
     this.submitProcurement=this.submitProcurement.bind(this);
     this.onAddProcurementClicked=this.onAddProcurementClicked.bind(this);

     const supplierOptions = [
       {value: 'value1', label: 'Value 1' },
       {value: 'value2', label: 'Value 2' }
     ];

     const itemSuggestions = [
       {value: 'barcode1', label: 'Colgate' },
       {value: 'barcode2', label: 'Maggie' },
       {value: 'barcode3', label: 'Lalitha rice' },
       {value: 'barcode4', label: 'Aashirvad Atta' }
     ];

     this.state = {
       renderAddProduct : false,
       renderAddNewProcurement : false,
       procurementDetails : {
         date : null,
         supplierOptions,
         supplierName : {'label' : '', 'value': ''},
         itemSuggestions,
         itemName : {'label' : '', 'value': ''},
         itemDetails : [],
         currentItemDetail: {},
         totalCost : 0
       },
       procurements : [{'name': '13 January 2018'}]
     }
   }


   // componentWillUnmount(){
   //   this.forceUpdate();
   // }

   addProduct(){
     this.setState({
       renderAddProduct : true
     })
   }

   onAddProcurementClicked(){
     this.setState({
       renderAddNewProcurement : true
     })
   }

   submitProcurement(){
     const { procurementDetails } = this.state;
     this.setState({
       renderAddNewProcurement : false
     })
   }


   valueEntered(fieldName,e) {
     console.log('---------');
     console.log(this.state);
      console.log(fieldName);
      console.log(e);

      //TODO confirm with Ram if this is the right way
      // const value = e.target.value; const newCurrentItemDetail = {};
      if(fieldName == 'supplierName')
        this.populateOptions(value);

      if(fieldName == 'quantity' || fieldName == 'costprice' || fieldName == 'sellingprice') {
        let  procurementDetails  =  { ...this.state.procurementDetails };
        let newCurrentItemDetail =  {};
        const currentItemDetail = procurementDetails.currentItemDetail;
        newCurrentItemDetail = { ...currentItemDetail , [fieldName] : e.target.value };
        procurementDetails.currentItemDetail = newCurrentItemDetail;
        this.setState({
          procurementDetails
        }, () => console.log(this.state));
      }


   }

   populateOptions(value) {
     //query here
     //this.setState
   }

   productDetailsAdded(){
     let { procurementDetails } = this.state;
     let itemDetails = procurementDetails.itemDetails;
     let barCode = procurementDetails.itemName.value;
     let currentItemDetail = procurementDetails.currentItemDetail;
     currentItemDetail = { ...currentItemDetail ,  name : procurementDetails.itemName.label , barcode : barCode}
     itemDetails.push(currentItemDetail);
     procurementDetails.totalCost+= Number(currentItemDetail.costprice);
     procurementDetails.itemName={'label' : '', 'value': ''};

      this.setState({
        renderAddProduct : false,
        procurementDetails : procurementDetails
      }, () => console.log(this.state));
   }

   closeLayer(){
     this.setState({
       renderAddProduct : false
     });
   }


   renderAddProduct() {
     const { renderAddProduct } = this.state;
     if(renderAddProduct){
       return (
         <Layer closer={true}
          flush={true} onClose={this.closeLayer.bind(this)}>
          <Form>
            <Header>
              <Heading>
                Product details ...
              </Heading>
            </Header>
              <div>
                    <FormField label='Enter quantity'>
                       <TextInput name="quantity" onDOMChange={this.valueEntered.bind(this, 'quantity')} />
                    </FormField>
                    <FormField label='Enter cost price'>
                        <TextInput name="quantity" onDOMChange={this.valueEntered.bind(this, 'costprice')} />
                    </FormField>
                    <FormField label='Enter selling price per unit'>
                        <TextInput name="quantity" onDOMChange={this.valueEntered.bind(this, 'sellingprice')} />
                    </FormField>
              </div>
            <Footer pad={{"vertical": "medium"}}>
              <Button label='Submit'
                type='submit'
                primary={true} onClick={this.productDetailsAdded.bind(this)}
               />
            </Footer>
            </Form>
            </Layer>
       )
     }else {
       return null;
     }
   }


   renderAddNewProcurement() {
     const { renderAddNewProcurement } = this.state;
     const { itemDetails , totalCost } = this.state.procurementDetails;
     //For rich suggestions
     // const OPTIONS = [
     //   {value: 'value1', label: <span><Status value="ok"/>Value 1</span> },
     //   {value: 'value2', label: <span><Status value="warning"/>Value 2</span> },
     // ];
     const { supplierOptions } = this.state.procurementDetails;
     const { itemSuggestions } = this.state.procurementDetails;

     if(!renderAddNewProcurement) {
       return null;
     }else {
       return (
         <div>
         <div className="dateField">
         <Label size="medium">Select date
           <DateTime id='id'
           name='name' value={this.state.procurementDetails.date} onChange={this.fieldEntered.bind(this, 'date')}
           />
         </Label>
         </div>
         <div className="supplier">
         <Label size="medium">Select supplier
         <SearchInput placeHolder='Search' onDOMChange={this.valueEntered.bind(this, 'supplierName')}
            onSelect={this.fieldEntered.bind(this, 'supplierName' )} value={this.state.procurementDetails.supplierName}
            suggestions={supplierOptions} />
          </Label>
         </div>
         <div className="payment">
         <Label size="medium">Type of payment
             <Select placeHolder='None'
              inline={false}
              multiple={false}
              onChange={this.fieldEntered.bind(this,'paymentType')}
              options={[{label : 'CARD', value : 'card'} , {label : 'CASH', value : 'cash'},
              {label : 'WALLET', value : 'wallet'}]}
              value={this.state.procurementDetails.paymentType}
              />
          </Label>
          </div>
          <div className="product">
          <Label size="medium">Select product
          <SearchInput placeHolder='Type Item Name' onDOMChange={this.valueEntered.bind(this, 'itemName')}
             onSelect={this.fieldEntered.bind(this, 'itemName' )}
             suggestions={itemSuggestions} value={this.state.procurementDetails.itemName} />
             <Button  className="addProduct" icon={<Add />}
             label='Add'
             onClick={this.addProduct}/>
          </Label>
          </div>
          <div className="tableSummary">
              <Value value={totalCost}
                label='Total Cost'
                units='$'
                responsive={false}
                reverse={false}
                align='end' />
                <Value value={itemDetails.length}
                label='No. of Items'
                responsive={false}
                reverse={false}
                align='end' />
          </div>
          <Table  className="tableContent" scrollable={true}>
            <thead>
              <tr>
              <th>
                Id
              </th>
              <th>
              Name
              </th>
              <th>
              Quantity
              </th>
              <th>
              Cost price
              </th>
              <th>
              Selling price per unit
              </th>
              <th>
                Discount
              </th>
              <th>
                Actions
              </th>
              </tr>
              </thead>
              <tbody>
                {
                  itemDetails.map((item, index) => {
                    return <TableRow>
                    <td>{index+1}</td>
                    <td>{item['name']}</td>
                    <td>{item['quantity']}</td>
                    <td>{item['costprice']}</td>
                    <td>{item['sellingprice']}</td>
                    <td>0</td>
                    <td>
                      <Button icon={<Edit />}
                        label='Edit'
                        plain={true} />
                        <Button icon={<Close />}
                          label='Delete'
                          plain={true} />
                  </td>

                    </TableRow>
                  })
                }
                </tbody>
                </Table>

                <Footer pad={{"vertical": "medium"}}>
                  <Button label='Submit'
                    type='submit'
                    primary={true} onClick={this.submitProcurement.bind(this)}
                   />
                </Footer>
                </div>

       )
     }

   }


   fieldEntered(fieldName,e, a, b){
     console.log(fieldName + 'clicked');
     console.log(e);
     console.log(a);
     console.log(b);

     const { procurementDetails } =  this.state ;
     if(fieldName == 'date')
      procurementDetails.date=e;
     if(fieldName == 'supplierName')
      procurementDetails.supplierName= e.suggestion;
     if(fieldName == 'paymentType')
       procurementDetails.paymentType= e.option;
     if(fieldName == 'itemName')
      procurementDetails.itemName= e.suggestion;




     this.setState(procurementDetails);
     console.log(this.state);


   }

   renderProcurementsHomePage() {
     const { procurements , renderAddNewProcurement } = this.state;
     if(!renderAddNewProcurement) {
       return (
         <div>
         <div className="addEntity">
         <Button icon={<Add />}
           label='Add Procurement'
           onClick={this.onAddProcurementClicked.bind(this)} primary='true'/>

         </div>
         <Table  className="tableContent" scrollable={true}>
           <thead>
             <tr>
             <th>
               Serial #
             </th>
             <th>
               Added on
             </th>
             </tr>
             </thead>
             <tbody>
               {
                 procurements.map((item, index) => {
                   return <TableRow>
                   <td>{index+1}</td>
                   <td>{item['name']}</td>
                   </TableRow>
                 })
               }
               </tbody>
               </Table>
               </div>
       )
     } else {
       return null;
     }

   }

   render(){

     return(
       <div className="procurements">
          { this.renderAddProduct() }
          { this.renderAddNewProcurement()}
          { this.renderProcurementsHomePage()}
       </div>


     );
   }

}


export default Procurements;
