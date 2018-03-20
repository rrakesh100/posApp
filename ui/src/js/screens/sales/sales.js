import React from 'react';
import  { allSales }   from '../../actions/sales';
import Table from 'grommet/components/Table';
import TableRow from 'grommet/components/TableRow';
import Button from 'grommet/components/Button';
import Edit from 'grommet/components/icons/base/Edit';
import MoreIcon from 'grommet/components/icons/base/More';
import Layer from 'grommet/components/Layer';
import Sale from './sale';




class sales extends React.Component {

  constructor() {
    super();
    this.state={
      sales : [],
      viewSaleModal : false,
      invoiceNumberForModal : ''
    };
    this.viewSale = this.viewSale.bind(this);
    this.renderViewSaleModal = this.renderViewSaleModal.bind(this);
  }


  componentDidMount() {
    allSales().then((response) => {
      this.setState({
        sales : response.data
      })
    }).catch(e => console.log(e));
  }

renderViewSaleModal() {
  const { viewSaleModal, invoiceNumberForModal } = this.state;
  if(!viewSaleModal)
    return null;
  else {
    return (
      <Layer
        onClose={this.onCloseModal.bind(this)}
        className='itemModal'
        closer={true}
        hidden={!viewSaleModal}
        component={Sale}
        >
        <Sale invoiceNumber={invoiceNumberForModal}/>
      </Layer>

    )
    }
}


  onCloseModal() {
    this.setState({
      viewSaleModal: false,
      invoiceNumberForModal: ''
    });
  }

viewSale(invoiceNumber) {

  console.log(invoiceNumber);
  this.setState({
    viewSaleModal : true,
    invoiceNumberForModal : invoiceNumber
  })

}

  render() {

    const { sales } = this.state;

    return (
      <div className="items">
      { this.renderViewSaleModal() }
      <div className="table">
      <Table scrollable={true}>
        <thead>
          <tr>
          <th>
            Invoice #
          </th>
          <th>
            Customer Name
          </th>
          <th>
            Customer #
          </th>
          <th>
           Time
          </th>
          <th>
          </th>
          </tr>
          </thead>
          <tbody>
            {
              sales.map((sale, index) => {
                return <TableRow>
                <td>{sale.invoiceNumber}</td>
                <td>{sale.customerName}</td>
                <td>{sale.customerMobileNumber}</td>
                <td>{sale.saleTime}</td>
                <td>
                  <Button icon={<MoreIcon />}
                    label='More details'
                    onClick={this.viewSale.bind(this, sale.invoiceNumber)}
                    plain={true} />
              </td>
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

export default sales;
