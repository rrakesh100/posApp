import React from 'react';
import Label from 'grommet/components/Label';
import SearchInput from 'grommet/components/SearchInput';
import Headline from 'grommet/components/Headline';
import Heading from 'grommet/components/Heading';
import Table from 'grommet/components/Table';
import TableRow from 'grommet/components/TableRow';
import Value from 'grommet/components/Value';
import Select from 'grommet/components/Select';
import Footer from 'grommet/components/Footer';
import Button from 'grommet/components/Button';
import {
  getCustomersWithPattern , getCustomer
} from '../../actions/customers';
import {
  getItem, getFilteredItems
} from '../../actions/items'
import {
  createBill
} from '../../actions/sales'


class Billing extends React.Component {

  constructor(){
    super();
    const itemSuggestions = [
      {value: 'barcode1', label: 'Colgate' },
      {value: 'barcode2', label: 'Maggie' },
      {value: 'barcode3', label: 'Lalitha rice' },
      {value: 'barcode4', label: 'Aashirvad Atta' }
    ];
    this.state = {
      customerMobileNumber : '',
      mobileInput : '',
      itemNameInput : '',
      customerName: 'Anonymous',
      itemSuggestions,
      items: [],
      itemName: '',
      totalCost:0,
      paymentType: '',
      showNewBill:true,
      mobileOptions : ['9901250919']
    }
    this.valueEntered=this.valueEntered.bind(this);
    this.valueSelected=this.valueSelected.bind(this);
    this.printBill=this.printBill.bind(this);
  }

  printBill(){
    //clear items, make server request with bill info
    const  {
      customerMobileNumber,
      customerName,
      paymentType,
      items,
      subTotal,
      discount,
      netAmount=0,
      taxAmount,
      taxPercent,
      sgst,
      cgst,
      igst=0,
      total
    } = this.state;


    const payload = {
      gstRegistrationNumber : "GST123",
      customerMobileNumber,
      customerName,
      paymentType,
      employeeId : 1,
      shopName : "More Super Market",
      saleItems:items,
      subTotal,
      discount,
      netAmount,
      taxAmount,
      taxPercent,
      cgst,
      sgst,
      igst,
      total
    }

    console.log(this.state);
    console.log(payload);

    createBill(payload).then(()=>{
      console.log("successfully created");
    }).catch(console.log("error occured while creating bill"))


    this.setState({})
    this.setState({
      showNewBill : true
    })
  }

  valueEntered(field, event){
    console.log(field);

    if(field === 'customerMobileNumber') {
      let { mobileInput } = this.state;
      mobileInput=event.target.value;
      getCustomersWithPattern(mobileInput).then((response) => {
           this.setState({
              mobileOptions : response.data
           })
      }).catch(()=>console.log('error occured'));
      this.setState({
        mobileInput
      })
    }else if(field === 'itemName') {
      let { itemNameInput } = this.state;

      itemNameInput=event.target.value;

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
          itemSuggestions :  itemSuggestions
         })
      }).catch(() => console.log('error occured while fetching getItem'));
    }else if(field === 'paymentType') {
      this.setState({
        paymentType:event.option.value
      })
    }

  }


componentDidMount() {

}



  valueSelected(field, target){
    console.log(field);
    console.log(target);
    if(field === 'customerMobileNumber') {
      this.setState({
        customerMobileNumber:target.suggestion,
        mobileInput : '',
        showNewBill:false
      });
      getCustomer(target.suggestion).then((response) => {
        console.log(response);
        this.setState({
          customerName : response.data.name
        })
      }).catch(() => console.log('error occured while trying to get customer related info'));
    }else if(field === 'itemName') {
      this.setState({
        itemName:target.suggestion,
        itemNameInput : ''
      },() => console.log(this.state));

      //query /v1/items/barcode and get the item details barcode=target.suggestion.value
      //we could have avoid this query by looking into filtereditems but still...live with it for now..

      getItem(target.suggestion.value).then((response) => {
        let result = response.data;
        console.log(result);
        let { items } = this.state;
        let quantity = 1;        let itemdiscount = 0;
        let  totalItemCost= Number(result.price) * quantity;
        let item = {
          itemId : target.suggestion.value,
          itemName : target.suggestion.label,
           quantity,
           price : result.price,
           discount :itemdiscount,
           totalPrice :totalItemCost
        }

        let { subTotal=0 ,totalCost=0, discount=0, netAmount=0 } = this.state;

        subTotal = subTotal + totalItemCost;
        netAmount = subTotal - discount;
        let cgst = (0.025*netAmount).toFixed(2);
        let sgst = (0.025*netAmount).toFixed(2);
        this.setState({
                items : items.concat(item),
                subTotal,
                discount,
                netAmount ,
                cgst ,
                sgst,
                total : (Number(netAmount) + Number(cgst) + Number(sgst)).toFixed(2),
              })
      } ).catch(()=>console.log('could not get item details'))

    }
  }

  render(){
    const { mobileOptions, customerName ,itemSuggestions, items, subTotal=0,discount=0,sgst=0,cgst=0,total=0,
       showNewBill } = this.state;
    return (
      <div>
        <Headline align="center" margin="large">
          MORE SUPER MARKET
        </Headline>
        <div className="customerMobileNumber">
        {showNewBill &&
        <Label size="medium">Customer Mobile Number
        <SearchInput placeHolder='Enter the mobile number' onDOMChange={this.valueEntered.bind(this, 'customerMobileNumber')}
           onSelect={this.valueSelected.bind(this, 'customerMobileNumber' )} value={this.state.mobileInput}
           suggestions={mobileOptions} />
           </Label> }
        </div>
       { !showNewBill  &&  <div>
          <Heading className="customerName" uppercase={true}
            align='start'
            margin='large' tag='h4'>
             Billing for : {customerName}
          </Heading>
          <Label size="medium">Add product
          <SearchInput placeHolder='Type Item Name' onDOMChange={this.valueEntered.bind(this, 'itemName')}
             onSelect={this.valueSelected.bind(this, 'itemName' )}
             suggestions={itemSuggestions} value={this.state.itemNameInput} />
          </Label>
          <div className="tableSummary">
            <Value value={items.length}
            label='No. of Items'
            responsive={false}
            reverse={false}
            align='end' />
          <Value value={subTotal}
              label='Sub Total'
              units='&#8377;'
              responsive={false}
              reverse={false}
              align='end' />
          <Value value={discount}
                label='Discount'
                units='&#8377;'
                responsive={false}
                reverse={false}
                align='end' />
              <Value value={Number(sgst) + Number(cgst)}
                label='GST'
                units='&#8377;'
                responsive={false}
                reverse={true}
                align='end' />
              <Value value={total}
              label='Total Cost'
              units='&#8377;'
              responsive={false}
              reverse={true}
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
               Unit Price
              </th>
              <th>
               Total Price
              </th>
              </tr>
              </thead>
              <tbody>
                {
                  items.map((item, index) => {
                    return <TableRow>
                    <td>{index+1}</td>
                    <td>{item['itemName']}</td>
                    <td>{item['quantity']}</td>
                    <td>{item['price']}</td>
                    <td>{item['totalPrice']}</td>
                    </TableRow>
                  })
                }
                </tbody>
                </Table>

                <div className="payment">
                <Label size="medium">Type of payment
                    <Select placeHolder='None'
                     inline={false}
                     multiple={false}
                     onChange={this.valueEntered.bind(this,'paymentType')}
                     options={[{label : 'CARD', value : 'card'} , {label : 'CASH', value : 'cash'},
                     {label : 'WALLET', value : 'wallet'}]}
                     value={this.state.paymentType}
                     />
                 </Label>
                 </div>

                 <Footer pad={{"vertical": "medium"}}>
                   <Button label='Submit'
                     type='submit'
                     primary={true} onClick={this.printBill.bind(this)}
                    />
                 </Footer>
                 </div>

               }

      </div>
    )
  }

}
export default Billing;
