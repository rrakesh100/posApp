import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { BrowserRouter as Router, Route, Switch, IndexRoute } from 'react-router-dom';

import App from 'grommet/components/App';
import Split from 'grommet/components/Split';

import NavSidebar from './NavSidebar';
import { navResponsive } from '../actions/nav';

import Login from '../screens/Login';
import Dashboard from '../screens/Dashboard';
import Tasks from '../screens/Tasks';
import Task from '../screens/Task';
import NotFound from '../screens/NotFound';
import Items from '../screens/items/Items';
import Item from '../screens/items/Item';
import Customers from '../screens/customers/Customers';
import Customer from '../screens/customers/Customer';
import Employees from '../screens/employees/Employees';
import Employee from '../screens/employees/Employee';
import Procurement from '../screens/procurements/Procurements'
import Billing from '../screens/billing/Billing'

class Main extends Component {
  constructor() {
    super();
    this._onResponsive = this._onResponsive.bind(this);
  }

  _onResponsive(responsive) {
    this.props.dispatch(navResponsive(responsive));
  }


  render() {
    const {
      nav: { active: navActive, enabled: navEnabled, responsive }
    } = this.props;
    const includeNav = (navActive && navEnabled);

    const dumy = () => (<div>blabla</div>);

    let nav;
    if (includeNav) {
      nav = <NavSidebar />;
    }
    const priority = (includeNav && responsive === 'single' ? 'left' : 'right');

    return (
      <App centered={false}>
        <Router>
          <Split
            priority={priority}
            flex='right'
            onResponsive={this._onResponsive}
          >
            {nav}
            <Switch>
              <Route exact={true} path='/' component={Dashboard} />
              <Route path='/dashboard' component={Dashboard} />
              <Route path='/login' component={Login} />
              <Route path='/tasks/:id' component={Task} />
              <Route path='/tasks' component={Tasks} />
              <Route path='/items' component={Items} />
              <Route path='/item/:id' component={Item} />
              <Route path='/customers' component={Customers} />
              <Route path='/customer/:id' component={Customer} />
              <Route exact={true} path='/employees' component={Employees} />
              <Route path='/employee/:id' component={Employee} />
              <Route path='/procurements' component={Procurement} />
              <Route path='/billing' component={Billing} />
              <Route path='/*' component={NotFound} />
            </Switch>
          </Split>
        </Router>
      </App>
    );
  }
}

Main.defaultProps = {
  nav: {
    active: true, // start with nav active
    enabled: true, // start with nav disabled
    responsive: 'multiple'
  }
};

Main.propTypes = {
  dispatch: PropTypes.func.isRequired,
  nav: PropTypes.shape({
    active: PropTypes.bool,
    enabled: PropTypes.bool,
    responsive: PropTypes.string
  })
};

const select = state => ({
  nav: state.nav
});

export default connect(select)(Main);
