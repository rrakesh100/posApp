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



class Procurements extends React.Component {

   constructor(){
     super();
   }

   render(){
     const items = [];
     return(
       <div className="procurements">
           <div className="dateField">
           <Label size="medium">Select date
             <DateTime id='id'
             name='name'
             />
           </Label>
           </div>
           <div className="supplier">
           <Label size="medium">Select supplier
           <SearchInput placeHolder='Search'
              suggestions={['first', 'second', 'third', 'fourth']} />
              </Label>
           </div>
           <div className="payment">
           <Label size="medium">Type of payment
               <Select placeHolder='None'
                inline={false}
                multiple={false}
                // onSearch={...} onChange={...}
                options={['DEBIT CARD', 'CREDIT CARD', 'CASH', 'WALLET']}
                />
            </Label>
            </div>
            <div className="product">
            <Label size="medium">Select product
            <SearchInput placeHolder='Search'
               suggestions={['Colgate', 'Maggie', 'Lalitha rice', 'Aashirvad Atta']} />
               <Button  className="addProduct" icon={<Add />}
               label='Add'
               href='#' />
            </Label>
            </div>
            <div className="tableSummary">
                <Value value={75}
                  label='Total Cost'
                  units='$'
                  responsive={false}
                  reverse={false}
                  align='end' />
                  <Value value={4}
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
                    items.map((item) => {
                      return <TableRow>
                      <td>{item.id}</td>
                      <td>{item.name}</td>
                      <td>{item.sku}</td>
                      <td>{item.description}</td>
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
       </div>


     );
   }

}


export default Procurements;
