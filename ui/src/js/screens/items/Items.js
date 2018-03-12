import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { pageLoaded } from '../utils';
import {
  allItems, deleteItem
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
import TextInput from 'grommet/components/TextInput';
import Layer from 'grommet/components/Layer';
import Edit from 'grommet/components/icons/base/Edit';
import CloseIcon from 'grommet/components/icons/base/Close';
import Heading from 'grommet/components/Heading';

import Item from './Item';






class Items extends React.Component {

  constructor(){
    super();
    this.state = {
      showItemModal: false,
      items: []
    };

  }

  componentDidMount() {
  //  this.props.dispatch(loaditems());
    this.loadItems();
  }

  loadItems() {

    allItems().then((response) => {
      this.setState({
        items : response.data
      })
    }).catch(
      console.log("Error occured while loding the items")
    );
  }


  deleteItem(itemId){
    deleteItem(itemId).then((response) => {
      console.log(response)
    }).catch(e => (console.log(e)));
    this.setState({
      showDeleteModal: false,
      itemIdForModal: null
    })
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


  onDeleteClick(id, event) {
     event.preventDefault();
    // this.props.dispatch(push(this.props.path));
    this.setState({
      showDeleteModal: true,
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
      showDeleteModal:false,
      itemIdForModal: ''
    });
  }

  onItemSubmit(flow , result , itemName) {
    let successMsg="", errorMsg = "";
    if(result){
      successMsg = "Item " +  itemName + (flow == 'add' ? ' added ' : ' edited ') + ' successfully ';
    }else {
      successMsg = "Error occured while " + (flow == 'add' ? ' add ' : ' edit ')  +  "ing the Item : " + itemName;
    }
    this.setState({
      showItemModal: false,
      successMsg,
      errorMsg,
      itemIdForModal: ''
    }, this.loadItems.bind(this));
  }


  renderViewItemModal() {
    const { showItemModal, itemIdForModal } = this.state;

    if(!showItemModal)
      return ;

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

  renderDeleteItemModal() {
    const { showDeleteModal, itemIdForModal } = this.state;

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
              Are you sure you want to delete the item ?
            </Heading>
            <Button label='YES'
            onClick={this.deleteItem.bind(this,itemIdForModal)}
            primary={true} />
          <Button label='NO'
            onClick={this.onCloseModal.bind(this)}
            />
      </Layer>

    )
  }



  render() {
    const { items, add } = this.state;

    return (

      <div className="items">
      { this.renderToastIfAny() }
      { this.renderViewItemModal() }
      { this.renderDeleteItemModal() }

      <div className="addEntity">
        <Button
          label='Add Item'
          onClick={this.onAdditems.bind(this)}
          primary={true}  />
      </div>
      <div className="table">
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
          Price
          </th>
          <th>
          SKU
          </th>
          <th>
            Quantity
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
              items.map((item, index) => {
                return <TableRow>
                <td>{index+1}</td>
                <td>{item.name}</td>
                <td>{item.price}</td>
                <td>{item.sku}</td>
                <td>{item.quantity}</td>
                <td>{item.description}</td>
                <td>
                  <Button icon={<Edit />}
                    label='Edit'
                    onClick={this.onEditClick.bind(this, item.barcode)}
                    plain={true} />
                  <Button icon={<CloseIcon />}
                      label='Delete'
                      onClick={this.onDeleteClick.bind(this, item.barcode)}
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
