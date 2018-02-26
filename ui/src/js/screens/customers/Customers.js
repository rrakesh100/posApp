import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { pageLoaded } from '../utils';
import { allCustomers, deleteCustomer } from '../../actions/customers';
import Table from 'grommet/components/Table';
import TableRow from 'grommet/components/TableRow';
import Button from 'grommet/components/Button';
import FormFields from 'grommet/components/FormFields';
import FormField from 'grommet/components/FormField';
import Form from 'grommet/components/Form';
import Footer from 'grommet/components/Footer';
import Toast from 'grommet/components/Toast';
import Label from 'grommet/components/Label';
import Anchor from 'grommet/components/Anchor';
import TextInput from 'grommet/components/TextInput';
import Layer from 'grommet/components/Layer';
import Edit from 'grommet/components/icons/base/Edit';
import CloseIcon from 'grommet/components/icons/base/Close';
import Heading from 'grommet/components/Heading';
import Customer from './Customer';

class Customers extends React.Component {
  constructor() {
    super();
    this.state = {
      showCustomerModal : false,
      customers : []
    };
  }

  componentDidMount() {
    this.loadCustomers();
  }

  loadCustomers() {

  allCustomers().then((response) => {
    this.setState( {
      customers : response.data
    } )
  }).catch(

  );
}

deleteCustomer(customerId) {
  deleteCustomer(customerId).then((response) => {
    console.log(response);
  }).catch(e => console.log(e));
  this.setState({
    showDeleteModal : false,
    customerIdForModal : null
  })
}

onEditClick(id, event) {
this.setState({
  showCustomerModal: true,
  customerIdForModal: id
})

}

onDeleteClick(id, event) {
  event.preventDefault();
  this.setState({
    showDeleteModal : true,
    customerIdForModal : id
  })
}

onAddCustomers() {
  this.setState({
    showCustomerModal : true,
    customerIdForModal : 0
  })
}

  onCloseModal() {
    this.setState({
      showCustomerModal: false,
      showDeleteModal:false,
      customerIdForModal: ''
    });
  }

  renderDeleteCustomerModal() {
    const { showDeleteModal, customerIdForModal } = this.state;
    console.log("entered deleted customer modal");
    return (
      <Layer
        onClose={this.onCloseModal.bind(this)}
        className='itemModal'
        closer={true}
        overlayClose={true}
        hidden={!showDeleteModal}
        >
            <Heading tag='h3'
            uppercase={true}
            align='center'
            margin='small'>
              Are you sure you want to delete it ?
            </Heading>
            <Button label='YES'
            onClick={this.deleteCustomer.bind(this,customerIdForModal)}
            primary={true} />
          <Button label='NO'
            onClick={this.onCloseModal.bind(this)}
            />
      </Layer>

    )
  }

  onCustomerSubmit(flow, result, customerName) {
  console.log("submitted");
  let successMsg='', errorMsg='';
  if(result) {
    successMsg = customerName + (flow == 'add' ? 'added' : ' edited') + 'successfully';
  } else {
    errorMsg = 'Error occured while' + (flow == 'add' ? ' add' : ' edit') + 'ing';
  }
  this.setState({
    showCustomerModal : false,
    successMsg,
    errorMsg,
    customerIdForModal : ''
  }, this.loadCustomers.bind(this));
 }

  renderViewCustomerModal() {
  const { showCustomerModal, customerIdForModal } = this.state;

      if(!showCustomerModal)
        return ;

      return (
        <Layer
          onClose={this.onCloseModal.bind(this)}
          className='itemModal'
          closer={true}
          hidden={!showCustomerModal}
          component={Customer}
          >
          <Customer customerId={customerIdForModal} onSubmit={this.onCustomerSubmit.bind(this)}/>
        </Layer>

      )
    }

 render() {

   const { customers, add } = this.state;
   return (

     <div className='customers'>
     { this.renderViewCustomerModal()}
     { this.renderDeleteCustomerModal()}
     <div className="addEntity">
       <Button
         label='New Customer'
         onClick={this.onAddCustomers.bind(this)}
         primary={true}  />
     </div>
     <div className="table">
     <Table scrollable={true}>
       <thead>
         <tr>
         <th>
         id
         </th>
         <th>
         Name
         </th>
         <th>
         Mobile Number
         </th>
         <th>
           Points
         </th>
         <th>
         Actions
         </th>
         </tr>
         </thead>
         <tbody>
          {
            customers.map((customer, index) => {
              return <TableRow>
              <td>{index+1}</td>
              <td>{customer.name}</td>
              <td>{customer.mobileNumber}</td>
              <td>{customer.points}</td>
              <td>
                <Button icon={<Edit />}
                  label='Edit'
                  onClick={this.onEditClick.bind(this, customer.mobileNumber)}
                  plain={true} />
                <Button icon={<CloseIcon />}
                    label='Delete'
                    onClick={this.onDeleteClick.bind(this, customer.mobileNumber)}
                    plain={true} />
              </td>
              </TableRow>
            })
          }
         </tbody>
         </Table>
         </div>
   </div>
   );
 }

}

export default Customers;
