
import React from 'react';
import  { fetchSale }   from '../../actions/sales';
import Heading from 'grommet/components/Heading';
import Value from 'grommet/components/Value';
import Table from 'grommet/components/Table';
import TableRow from 'grommet/components/TableRow';


class sale extends React.Component {

  constructor() {
    super();
    this.state = {
      invoiceNumber : ''
    }
  }

 componentDidMount() {
   console.log(this.props);
   this.setState({
      invoiceNumber : this.props.invoiceNumber
   })
   fetchSale(this.props.invoiceNumber).then((response) =>{
      this.setState({
        ...response.data
      })
   }).catch();
 }


  render() {
    console.log(this.state);
    const {saleItems} = this.state;
    return (
      <div>
      <div>
      <Heading>
          Customer Name
      </Heading>
      <Value value={this.state.customerName}
      responsive={false}
      reverse={false}
      align='end' />
      </div>
      <div>
      <Heading>
          Customer Mobile
      </Heading>
      <Value value={this.state.customerMobileNumber}
      responsive={false}
      reverse={false}
      align='end' />
      </div>
      <div>
      <Heading>
          SUB TOTAL
      </Heading>
      <Value value={this.state.subTotal}
      responsive={false}
      reverse={false}
      align='end' />
      </div>
      <div>
      <Heading>
          DISCOUNT
      </Heading>
      <Value value={this.state.discount}
      responsive={false}
      reverse={false}
      align='end' />
      </div>
      <div>
      <Heading>
          Net Amount
      </Heading>
      <Value value={this.state.netAmount}
      responsive={false}
      reverse={false}
      align='end' />
      </div>
      <div>
      <Heading>
          Tax Amount
      </Heading>
      <Value value={this.state.taxAmount}
      responsive={false}
      reverse={false}
      align='end' />
      </div>
      <div>
      <Heading>
          CGST
      </Heading>
      <Value value={this.state.cgst}
      responsive={false}
      reverse={false}
      align='end' />
      </div>
      <div>
      <Heading>
          SGST
      </Heading>
      <Value value={this.state.sgst}
      responsive={false}
      reverse={false}
      align='end' />
      </div>
      <div>
      <Heading>
          Total
      </Heading>
      <Value value={this.state.total}
      responsive={false}
      reverse={false}
      align='end' />
      </div>
      <div className="table">
      <Table scrollable={true}>
        <thead>
          <tr>
          <th>
            Serial #
          </th>
          <th>
            Item Name
          </th>
          <th>
            Quantity
          </th>
          <th>
            Price
          </th>

          </tr>
          </thead>
          <tbody>
            {
              saleItems.map((item, index) => {
                return <TableRow>
                <td>{item.serialNumber }</td>
                <td>{item.itemName}</td>
                <td>{item.quantity}</td>
                <td>{item.price}</td>
                </TableRow>
              })
            }
            </tbody>
            </Table>
      </div>

      </div>



    )
  }
}

export default sale;
