import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { pageLoaded } from '../utils';
import {
  loadEmployees, unloadEmployees, saveEmployee, getEmployee
} from '../../actions/employees';

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

import { EMPLOYEES_ADD } from '../../actions';



class Employees extends React.Component {

  constructor(){
    super();
    this.onAddEmployee=this.onAddEmployee.bind(this);
    this.onSubmitEmployee=this.onSubmitEmployee.bind(this);
    this.state = {};
    this.onEditClick = this.onEditClick.bind(this);
  }

  onAddEmployee(){
    console.log('On Add Item clicked');
    this.props.dispatch({type:'EMPLOYEES_ADD'});
  }

  onSubmitEmployee(){
      const { productName, barCode, sku, description } = this.state;
      const data = {
        name : productName,
        description : description,
        barcode : barCode,
        sku: sku
      }
      let result = saveEmployee(data);
      result.then(function (response) {
      console.log(response);
      // this.setState({
      //   successMsg: `Product ${productName} has been successfully added.`
      // })
    })
    .catch(function (error) {
      console.log(error);
      //
      // this.setState({
      //   errMsg: `Uanbled to add ${productName}.`
      // })
    });
  }

  componentDidMount() {
    pageLoaded('Employees');
    console.log('component mounted');
    console.log(this.props);
    this.props.dispatch(loadEmployees());
    console.log('done');
  }

  componentWillUnmount() {
    this.props.dispatch(unloadEmployees());
  }

  onInputEntered(fieldName, e) {
    console.log(e);
    this.setState({
      [fieldName]: e.target.value
    });
  }

  onEditClick(event) {
    event.preventDefault();
    this.props.dispatch(push(this.props.path));
  }


  render() {
    const { employees, add } = this.props;
    console.log(this.props);
    if(add){
      return (
      <h1/>
      );
    }
    return (

      <div>
      { this.renderToastIfAny() }
      <Button
        label='Add Employee'
        onClick={this.onAddEmployee}
        href='#'
        primary={true}  />

      <Table scrollable={true}>
        <thead>
          <tr>
          <th>
            Id
          </th>
          <th>
          Name
          </th>
          <th>
          SKU
          </th>
          <th>
          Description
          </th>
          <th>
            Actions
          </th>
          </tr>
          </thead>
          <tbody>
            {
              employees.map((employe) => {
                return <TableRow>
                <td>{employe.id}</td>
                <td>{employe.name}</td>
                <td>{employe.sku}</td>
                <td>{employe.description}</td>
                <td><Label><Anchor path={`/employee/${employe.id}`} label='Edit' onClick={this.onEditClick} /></Label></td>
                </TableRow>
              })
            }
            </tbody>
            </Table>
      </div>
    );
  }



  renderToastIfAny() {
    const { errMsg, successMsg } = this.state;
    let status = 'ok', clearCallback, msg;
    const clearErrMsg = () => this.setState({errMsg: ''});
    const clearSuccessMsg = () => this.setState({successMsg: ''});

    if(this.state.errMsg) {
      status = 'critical';
      clearCallback = clearErrMsg;
      msg=errMsg;
    } else {
      clearCallback = clearSuccessMsg;
      msg=successMsg;
    }
    return (<Toast status={status}
      onClose={clearCallback}>
      {msg}
    </Toast>);



  }
}

Employees.defaultProps = {
  error: undefined,
  employees: []
};

Employees.propTypes = {
  dispatch: PropTypes.func.isRequired,
  employees: PropTypes.arrayOf(PropTypes.object)
};

const select = state => ({ ...state.employees });

export default connect(select)(Employees);
