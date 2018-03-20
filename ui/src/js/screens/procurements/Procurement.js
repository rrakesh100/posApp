import React, { PropTypes } from 'react';
import ReactDOM from 'react-dom';
import Procurements from './Procurements';
import { fetchProcurement } from '../../actions/procurements';
import Table from 'grommet/components/Table';
import TableRow from 'grommet/components/TableRow';
import Button from 'grommet/components/Button';
import TableHeader from 'grommet/components/TableHeader';

class Procurement extends React.Component {
  constructor(props) {
    super(props);
    // this.state = {
    //   procurementId : 0,
    //   date : null,
    //   comment : '',
    //   paymentAmount : 0,
    //   paymentType : '',
    //   procurementItems : []
    // }
    this.state = {};

}
componentDidMount() {
  const { procurementId } = this.props;

  if(procurementId) {

  let getProcurement = fetchProcurement(procurementId);
  getProcurement.then((response) => {
    console.log(response.data);
    const newState = response.data;
    this.setState(newState);
    console.log(newState);
    console.log(this.state)
  }).catch((e) => console.log(e));

} else {
  return
}
};

componentWillReceiveProps(nextProps) {
  const { procurementId } = this.nextProps;
  console.log(nextProps);
  console.log(procurementId);

  if(procurementId) {

  let getProcurement = fetchProcurement(procurementId);
  getProcurement.then((response) => {
  console.log(response.data);
  const newState = response.data;
  this.setState(newState);
  console.log(newState);
  console.log(this.state)
    }).catch((e) => console.log(e));

} else {
  return
}
};

render() {
  // console.log(this.state);
  const { procurementId, date, comment, paymentType, paymentAmount, procurementItems } = this.state;

  return (
<div>
<div className='table'>
<Table scrollable={true} >
  <TableHeader labels={['Name', 'Details']} />
  <tbody>
  <TableRow>
      <td>
      <strong>Id :</strong>
      </td>
      <td>
      {procurementId}
      </td>
  </TableRow>
  <TableRow>
      <td>
      <strong>Date :</strong>
      </td>
      <td>
      {date}
      </td>
  </TableRow>
  <TableRow>
      <td>
      <strong>Comment :</strong>
      </td>
      <td>
      {comment}
      </td>
  </TableRow>
  <TableRow>
      <td>
      <strong>Payment Type :</strong>
      </td>
      <td>
      {paymentType}
      </td>
  </TableRow>
  <TableRow>
      <td>
      <strong>Payment Amount :</strong>
      </td>
      <td>
      {paymentAmount}
      </td>
  </TableRow>
  </tbody>
  </Table>
  </div>
  <div className='procurementItems'>
  <Table scrollable={true} responsive={false}>
    <TableHeader labels={['S.No', 'Item', 'Quantity', 'Item Cost', 'Unit Price']} />
    <tbody>
    {
      procurementItems.map((procurement, index) => {
        return <TableRow key={index}>
        <td>{procurement.serialNumber}</td>
        <td>{procurement.description}</td>
        <td>{procurement.quantityPurchased}</td>
        <td>{procurement.itemCostPrice}</td>
        <td>{procurement.itemUnitPrice}</td>
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

export default Procurement
