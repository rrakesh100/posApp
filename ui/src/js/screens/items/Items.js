import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { pageLoaded } from '../utils';
import {
  allItems
} from '../../actions/items';

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
import { push } from 'react-router-redux';
import TextInput from 'grommet/components/TextInput';
import Layer from 'grommet/components/Layer';
import Edit from 'grommet/components/icons/base/Edit';

import Item from './Item';






class Items extends React.Component {

  constructor(){
    super();
    this.state = {
      showItemModal: false
    };

  }

  componentDidMount() {
  //  this.props.dispatch(loaditems());
    this.loadItems();
  }

  loadItems() {
    allItems().then((response) => {
      this.setState( {
        items : response.data
      }
        )
    }).catch(

    );
  }

  componentWillUnmount() {
    //should we clear the state everytime component is unmounted ?
  }


  onEditClick(id, event) {
    // event.preventDefault();
    // this.props.dispatch(push(this.props.path));
    this.setState({
      showItemModal: true,
      itemIdForModal: id
    })

  }


  onAdditems() {
    this.setState({
      showItemModal: true,
      itemIdForModal: 0
    })
  }

  onCloseModal() {
    this.setState({
      showItemModal: false,
      itemIdForModal: ''
    });
  }

  onItemSubmit() {
    this.setState({
      showItemModal: false,
      itemIdForModal: ''
    }, this.loadItems.bind(this));
  }


  renderItemModal() {
    const { showItemModal, itemIdForModal } = this.state;

    return (
      <Layer
        onClose={this.onCloseModal.bind(this)}
        className='itemModal'
        closer={true}
        hidden={!showItemModal}
        component={Item}
        >
        <Item itemId={itemIdForModal} onSubmit={this.onItemSubmit.bind(this)}/>
      </Layer>

    )
  }


  render() {
    const { items, add } = this.state;
    if(add){
      return (
      <h1/>
      );
    }
    return (

      <div className="items">
      { this.renderToastIfAny() }
      { this.renderItemModal() }
      <Button
        label='Add Item'
        onClick={this.onAdditems.bind(this)}
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
              items.map((item) => {
                return <TableRow>
                <td>{item.id}</td>
                <td>{item.name}</td>
                <td>{item.sku}</td>
                <td>{item.description}</td>
                <td>
                  <Button icon={<Edit />}
                    label='Edit'
                    onClick={this.onEditClick.bind(this, item.id)}
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



  renderToastIfAny() {
    const { errMsg, successMsg } = this.state;
    let status = 'ok', clearCallback, msg;
    const clearErrMsg = () => this.setState({errMsg: ''});
    const clearSuccessMsg = () => this.setState({successMsg: ''});

    if(this.state.errMsg) {
      status = 'critical';
      clearCallback = clearErrMsg;
      msg=errMsg;
      return (<Toast status={status}
        onClose={clearCallback}>
        {msg}
      </Toast>);
    } else if(this.state.successMsg){
      clearCallback = clearSuccessMsg;
      msg=successMsg;
      return (<Toast status={status}
        onClose={clearCallback}>
        {msg}
      </Toast>);
    }

    return null;
  }

}

Items.defaultProps = {
  error: undefined,
  items: []
};

Items.propTypes = {
  dispatch: PropTypes.func.isRequired,
  items: PropTypes.arrayOf(PropTypes.object)
};

const select = state => ({ ...state.items });

export default connect(select)(Items);
