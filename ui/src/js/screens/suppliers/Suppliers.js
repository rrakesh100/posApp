import React, {PropTypes} from 'react'
import { connect } from 'react-redux';
import { pageLoaded } from '../utils';
import { allSuppliers, deleteSupplier } from '../../actions/suppliers'
import Button from 'grommet/components/Button'
import Table from 'grommet/components/Table'
import TableRow from 'grommet/components/TableRow'
import Layer from 'grommet/components/Layer'
import Heading from 'grommet/components/Heading';
import CloseIcon from 'grommet/components/icons/base/Close'
import Edit from 'grommet/components/icons/base/Edit'
import TextInput from 'grommet/components/TextInput'
import FormFields from 'grommet/components/FormFields'
import FormField from 'grommet/components/FormField'
import Form from 'grommet/components/Form'
import Footer from 'grommet/components/Footer'
import Toast from 'grommet/components/Toast'
import Anchor from 'grommet/components/Anchor'
import Label from 'grommet/components/Label'
import Supplier from './Supplier'

class Suppliers extends React.Component {
  constructor() {
    super();
    this.state = {
      showSupplierModal : false,
      suppliers : []
    }
  }
  componentDidMount() {
    this.loadSuppliers()
  }

  loadSuppliers() {

    allSuppliers().then((response) => {
      this.setState({
        suppliers : response.data
      })
      }).catch(
      console.log("Error occured while loading the page")
    );
  }

onAddSuppliers() {
  this.setState({
    showSupplierModal : true,
    supplierIdForModal : 0
  })
}

onEditClick(id, event) {
  this.setState({
    showSupplierModal : true,
    supplierIdForModal : id
  })
}

onDeleteClick(id, event) {
  event.preventDefault()
  this.setState({
    showDeleteModal : true,
    supplierIdForModal : id
  })
}

onCloseModal() {
  this.setState({
    showSupplierModal : false,
    showDeleteModal : false,
    supplierIdForModal : ''
  })
}

onSupplierSubmit(flow, result, supplierName) {
  let successMsg='', errorMsg=''
  if(result) {
    successMsg = supplierName + (flow == 'add' ? 'added' : 'edited') + 'successfully'
  }else {
      errorMsg = 'Error occured while' + (flow == 'add' ? 'adding' : 'editing')
    }
  this.setState({
    showSupplierModal : false,
    successMsg,
    errorMsg,
    supplierIdForModal : ''
  }, this.loadSuppliers.bind(this));
}

renderViewSupplierModal() {
  const { showSupplierModal, supplierIdForModal } = this.state

  if(!showSupplierModal)
  return;

  return (
    <Layer
    onClose={this.onCloseModal.bind(this)}
    className="itemModal"
    closer={true}
    hidden={!showSupplierModal}
    component={Supplier}>
       <Supplier supplierId={supplierIdForModal} onSubmit={this.onSupplierSubmit.bind(this)} />
    </Layer>
  )
}

deleteSupplier(supplierId) {
  deleteSupplier(supplierId).then(function(response) {
    console.log(response);
  }).catch(e => console.log(e));
  this.setState({
    showDeleteModal : false,
    supplierIdForModal : null
  })
}

renderDeleteSupplierModal() {
  const { showDeleteModal, supplierIdForModal } = this.state
  console.log("entered deleted supplier modal");
  return (
    <Layer onClose={this.onCloseModal.bind(this)}
    className='itemModal'
    closer={true}
    overlayClose={true}
    hidden={!showDeleteModal}
    >
      <Heading tag='h3'
      uppercase={true}
      align='center'
      margin='small'>
      Are you sure you want to delete it?
      </Heading>
      <Button label='YES'
      onClick={this.deleteSupplier.bind(this, supplierIdForModal)}
      primary={true}
      />
      <Button label='NO'
      onClick={this.onCloseModal.bind(this)}
      />
    </Layer>
  )
}

render() {
  const { suppliers, add } = this.state;
  return (
    <div className="suppliers">
    {this.renderViewSupplierModal()}
    {this.renderDeleteSupplierModal()}
    <div className="addEntity">
      <Button
      label='New Supplier'
      onClick={this.onAddSuppliers.bind(this)}
      primary={true}
    />
    </div>
    <div className="table">
      <Table scrollable={true}>
       <thead>
        <tr>
          <th>Id</th>
          <th>Mobile Number</th>
          <th>Company Name</th>
          <th>Agency Name</th>
          <th>Actions</th>
        </tr>
       </thead>
       <tbody>
         {
           suppliers.map((supplier, index) => {
             return <TableRow>
             <td>{index+1}</td>
             <td>{supplier.mobileNumber}</td>
             <td>{supplier.companyName}</td>
             <td>{supplier.agencyName}</td>
             <td>
                <Button icon={<Edit />}
                label="Edit"
                onClick={this.onEditClick.bind(this, supplier.id)}
                plain={true}
                />
             </td>
             <td>
                <Button icon={<CloseIcon />}
                label="Delete"
                onClick={this.onDeleteClick.bind(this, supplier.id)}
                plain={true}
                />
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

export default Suppliers
