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
        let { items , totalCost } = this.state;
        let item = {
           name : target.suggestion.label,
           quantity : 1,
           price : result.price,
           discount : 0,
           totalPrice : result.price
        }
        totalCost+= Number(item.totalPrice);
        this.setState({
                items : items.concat(item),
                totalCost
              })
      } ).catch(()=>console.log('could not get item details'))





    }
  }

  render(){
    const { mobileOptions, customerName ,itemSuggestions, items, totalCost, showNewBill } = this.state;
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
            <Value value={totalCost}
              label='Total Cost'
              units='$'
              responsive={false}
              reverse={false}
              align='end' />
            <Value value={totalCost}
              label='Bill Amount (Incl Tax)'
              units='$'
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
                    <td>{item['name']}</td>
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
