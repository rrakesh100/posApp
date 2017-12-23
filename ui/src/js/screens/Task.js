import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import Anchor from 'grommet/components/Anchor';
import Article from 'grommet/components/Article';
import Box from 'grommet/components/Box';
import Header from 'grommet/components/Header';
import Heading from 'grommet/components/Heading';
import Label from 'grommet/components/Label';
import Meter from 'grommet/components/Meter';
import Notification from 'grommet/components/Notification';
import Value from 'grommet/components/Value';
import Spinning from 'grommet/components/icons/Spinning';
import LinkPrevious from 'grommet/components/icons/base/LinkPrevious';
import Table from 'grommet/components/Table';
import TableRow from 'grommet/components/TableRow';
import Search from 'grommet/components/Search';


import {
  loadTask, unloadTask
} from '../actions/tasks';

import { pageLoaded } from './utils';

class Task extends Component {

  constructor(props) {
    super(props);
    this.state = {
      searchStr: ''
    };
  }
  componentDidMount() {
    const { match: { params }, dispatch } = this.props;
    pageLoaded('Task');
    dispatch(loadTask(params.id));
  }

  componentWillUnmount() {
    const { match: { params }, dispatch } = this.props;
    dispatch(unloadTask(params.id));
  }

  render() {
    const { error, task } = this.props;

    let errorNode;
    let taskNode;
    if (error) {
      errorNode = (
        <Notification
          status='critical'
          size='large'
          state={error.message}
          message='An unexpected error happened, please try again later'
        />
      );
    } else if (!task) {
      taskNode = (
        <Box
          direction='row'
          responsive={false}
          pad={{ between: 'small', horizontal: 'medium', vertical: 'medium' }}
        >
          <Spinning /><span>Loading...</span>
        </Box>
      );
    } else {
      taskNode = (
        <Box pad='medium'>
          <Label>Status: {task.status}</Label>
          <Box
            direction='row'
            responsive={false}
            pad={{ between: 'small' }}
          >
            <Value
              value={task.percentComplete}
              units='%'
              align='start'
              size='small'
            />
            <Meter value={task.percentComplete} />
          </Box>
        </Box>
      );
    }

    return (
      <Article primary={true} full={true}>
        <Header
          direction='row'
          size='large'
          colorIndex='light-2'
          align='center'
          responsive={false}
          pad={{ horizontal: 'small' }}
        >
          <Anchor path='/tasks'>
            <LinkPrevious a11yTitle='Back to Tasks' />
          </Anchor>
          <Heading margin='none' strong={true}>
            {'BILL'}
          </Heading>
        </Header>
        {errorNode}
        { this.renderSearch() }
        { this.getDummyTable() }
      </Article>
    );
  }

  renderSearch() {
    return (
      <Search placeHolder='Search'
        inline={true}
        size='large'
        dropAlign={{"right": "right"}}
        iconAlign='start'
        value={this.state.searchStr}
        onDOMChange={this.setUserInput.bind(this) }
      />
    );
  }

  setUserInput(e) {
    this.setState({
      searchStr: e.target.value
    });
  }

  getDummyTable() {
    // FETCH FROM BACKEND
    return (
      <Table scrollable={true}
        selectable={true}
      >
        <thead>
          <tr>
            <th>
              Id
            </th>
            <th>
              Product
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
          <TableRow>
            <td>
              1
            </td>
            <td>
              Pencil
            </td>
            <td className='secondary'>
              2x15
            </td>
            <td className='secondary'>
              30.00
            </td>
          </TableRow>
          <TableRow>
            <td>
              2
            </td>
            <td>
              Horlicks
            </td>
            <td className='secondary'>
              1x356.50
            </td>
            <td>
              356.50
            </td>
          </TableRow>
          <TableRow>
            <td>
              3
            </td>
            <td>
              Lalitha Green Rice
            </td>
            <td className='secondary'>
              2x1250
            </td>
            <td>
              2500.00
            </td>
          </TableRow>
          <TableRow>
            <td>
              4
            </td>
            <td>
              Lalitha Green Ravva
            </td>
            <td className='secondary'>
              1x250.00
            </td>
            <td>
              250.00
            </td>
          </TableRow>
        </tbody>
      </Table>
    );
  }
}

Task.defaultProps = {
  error: undefined,
  task: undefined
};

Task.propTypes = {
  dispatch: PropTypes.func.isRequired,
  error: PropTypes.object,
  match: PropTypes.object.isRequired,
  task: PropTypes.object
};

const select = state => ({ ...state.tasks });

export default connect(select)(Task);
