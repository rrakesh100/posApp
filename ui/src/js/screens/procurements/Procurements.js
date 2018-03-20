import React, { PropTypes } from 'react';
import Procurement from './Procurement';
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
import DocumentText from 'grommet/components/icons/base/DocumentText';
import {
  allSuppliers, getSuppliersWithPattern, getSupplier
} from '../../actions/suppliers'
import {
  getItem, getFilteredItems
} from '../../actions/items'
import {
  allProcurements, addProcurement
} from '../../actions/procurements'
import moment from 'moment'





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
     this.viewProcurementDetails=this.viewProcurementDetails.bind(this);
     this.closeProcurementLayer=this.closeProcurementLayer.bind(this);

     const supplierOptions = [
       {value: '', label: '' },
       {value: '', label: '' }
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
       renderProcurementDetails : false,
       procurementDetails : {
         date : null,
         supplierOptions,
         supplierName : {'label' : '', 'value': ''},
         itemSuggestions,
         itemName : {'label' : '', 'value': ''},
         procurementItems : [],
         currentItemDetail: {},
         paymentAmount : 0
       },
       procurements : [
         {
           comment : '',
           date : null,
           paymentAmount : 0,
           paymentType : '',
           procurementId : 0,
           procurementItems : [],
           supplierId : 0
         }
        ]
     }
   }


   // componentWillUnmount(){
   //   this.forceUpdate();
   // }

   componentDidMount() {
     this.allProcurements()
        }

    allProcurements() {
      const { procurements } = this.state
        allProcurements().then((response) => {
          this.setState({
            procurements : response.data
          })
          console.log(response.data)
          console.log(this.state)
          console.log(procurements)
        }).catch(e => console.log(e));
      }

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
   viewProcurementDetails(id) {
     this.setState({
       renderProcurementDetails : true,
       procurementIdForModal : id

     }, () => console.log(this.state))
   }

   submitProcurement(){
     console.log('#######################');
     const { procurementDetails, procurements } = this.state;

     let payload = {};

     payload.date=procurementDetails.date;
     payload.paymentType=procurementDetails.paymentType.value;
     payload.paymentAmount = procurementDetails.paymentAmount;
     payload.supplierId=procurementDetails.supplierName.value;
     payload.comment="hi"
     // payload.procurementItems=procurementDetails.procurementItems;

     let procurementItemsPayload =  procurementDetails.procurementItems;
     let itemsArray = [];
     procurementItemsPayload.map((item, index) => {
        let arrayItem = {};
        arrayItem.itemId = item.barcode;
       arrayItem.description = item.name;
       arrayItem.serialNumber = index;
       arrayItem.quantityPurchased = item.quantity;
       arrayItem.itemCostPrice = item.costPrice;
       arrayItem.itemUnitPrice = item.sellingprice;
       itemsArray.push(arrayItem);
 		})
    payload.procurementItems = itemsArray;
     console.log(payload)
     console.log(this.state)
     let createProcurement = addProcurement(payload);
     createProcurement.then((response) =>
     this.setState({
       renderAddNewProcurement : false
     }, this.allProcurements())
          )
        .catch(e => console.log(e));
   }

      populateOptions(value) {

        let { supplierNameInput } = this.state;
        const { procurementDetails } = this.state;
        supplierNameInput = value;
        this.state.procurementDetails.supplierName=supplierNameInput;
        this.setState({
          supplierNameInput
        })
        allSuppliers(supplierNameInput).then((response) => {
          let filteredSuppliers = response.data;
          let supplierOptions = [];
          for(let supplier of filteredSuppliers) {
            let suggestion = {
              value : supplier.id,
              label : supplier.companyName
              }
              supplierOptions.push(suggestion);
          }

          procurementDetails.supplierOptions = supplierOptions;
          this.setState({
            procurementDetails : procurementDetails
          })
          console.log(this.state)
          console.log(response.data)
        }).catch(() => console.log('error occured while fetching supplier details'))
      }

   valueEntered(fieldName,e) {
     console.log(this.state);
      console.log(fieldName);
      console.log(e);

      if(fieldName === 'supplierName') {
        this.populateOptions(e.target.value);
      }else if(fieldName === 'itemName') {
          let { itemNameInput } = this.state;

          itemNameInput=e.target.value;
          this.state.procurementDetails.itemName=itemNameInput;

          this.setState({
            itemNameInput
          })
          getFilteredItems(itemNameInput).then((response) => {
            let filteredItems = response.data;
            let itemSuggestions = [];
            for(let item of filteredItems) {
              let suggestion = {
                value : item.barcode,
                label : item.name
              }
              itemSuggestions.push(suggestion);
            }

            this.setState({
              itemSuggestions :itemSuggestions
             })
          }).catch(() => console.log('error occured while fetching getItem'));
        }

      if(fieldName == 'quantity' || fieldName == 'costPrice' || fieldName == 'sellingprice') {
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


   productDetailsAdded() {

     let { procurementDetails } = this.state;
     let procurementItems = procurementDetails.procurementItems;
     let barCode = procurementDetails.itemName.value;
     let currentItemDetail = procurementDetails.currentItemDetail;
     currentItemDetail = { ...currentItemDetail ,  name : procurementDetails.itemName.label , barcode : barCode}
     procurementItems.push(currentItemDetail);
     procurementDetails.paymentAmount+= Number(currentItemDetail.costPrice);
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
                        <TextInput name="quantity" onDOMChange={this.valueEntered.bind(this, 'costPrice')} />
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
     const { procurementItems , paymentAmount } = this.state.procurementDetails;
     //For rich suggestions
     // const OPTIONS = [
     //   {value: 'value1', label: <span><Status value="ok"/>Value 1</span> },
     //   {value: 'value2', label: <span><Status value="warning"/>Value 2</span> },
     // ];
     const { supplierOptions } = this.state.procurementDetails;
     const { itemSuggestions } = this.state;

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
              <Value value={paymentAmount}
                label='Total Cost'
                units='$'
                responsive={false}
                reverse={false}
                align='end' />
                <Value value={procurementItems.length}
                label='No. of Items'
                responsive={false}
                reverse={false}
                align='end' />
          </div>
          <Table  className="tableContent" scrollable={true} >
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
                  procurementItems.map((procurement, index) => {
                    return <TableRow key={index}>
                    <td>{index+1}</td>
                    <td>{procurement['name']}</td>
                    <td>{procurement['quantity']}</td>
                    <td>{procurement['costPrice']}</td>
                    <td>{procurement['sellingprice']}</td>
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


   fieldEntered(fieldName, e, a, b) {
     console.log(fieldName + 'clicked');
     console.log(e);
     console.log(a);
     console.log(b);

     const { procurementDetails } =  this.state ;
     if(fieldName == 'date') {
       var a = moment(e);
       console.log(a)
       procurementDetails.date =+a;
       console.log("time ============ " + a);
     }
     if(fieldName == 'supplierName')
      procurementDetails.supplierName = e.suggestion;
     if(fieldName == 'paymentType')
       procurementDetails.paymentType = e.option;
     if(fieldName == 'itemName')
      procurementDetails.itemName = e.suggestion;

     this.setState(procurementDetails);
     console.log(this.state);
 }

closeProcurementLayer() {
  this.setState({
    renderProcurementDetails : false
  })
}
 renderProcurementDetails() {
   console.log(this.state);
   const { procurements, renderProcurementDetails, procurementIdForModal } = this.state;
   console.log(procurements);
   if(renderProcurementDetails) {
     return (
       <Layer closer={true}
       overlayClose={true}
        flush={true} onClose={this.closeProcurementLayer.bind(this)}
        component={Procurement}>
        <Procurement procurementId={procurementIdForModal} />

       </Layer>
     );
   } else {
     return
   }
}

   renderProcurementsHomePage() {
     const { procurements , renderAddNewProcurement } = this.state;
     if(!renderAddNewProcurement) {
       return (
         <div>
         <div className="addEntity">
         <Button icon={<Add />}
           label='Add Procurement'
           onClick={this.onAddProcurementClicked.bind(this)} primary={true}/>

         </div>
         <Table  className="tableContent" scrollable={true} >
           <thead>
             <tr>
             <th>Id</th>
             <th>Date</th>
             <th>Comment</th>
             <th>Payment Amount</th>
             <th>Payment Type</th>
             <th>Details</th>
             </tr>
             </thead>
             <tbody>
               {
                 procurements.map((procurement, index) => {
                   return <TableRow key={index}>
                   <td>{index+1}</td>
                   <td>{procurement['date']}</td>
                   <td>{procurement['comment']}</td>
                   <td>{procurement['paymentAmount']}</td>
                   <td>{procurement['paymentType']}</td>
                   <td>
                     <Button icon={<DocumentText />}
                     label='view'
                     onClick={this.viewProcurementDetails.bind(this, procurement.procurementId)}
                     />
                   </td>
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
          { this.renderAddNewProcurement() }
          { this.renderProcurementsHomePage() }
          { this.renderProcurementDetails() }
       </div>
     );
   }

}

export default Procurements;
