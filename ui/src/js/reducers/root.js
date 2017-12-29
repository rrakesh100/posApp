import { combineReducers } from 'redux';

import dashboard from './dashboard';
import nav from './nav';
import session from './session';
import tasks from './tasks';
// import items from './items';
// import customers from './customers';
import employees from './employees';

export default combineReducers({
  dashboard,
  nav,
  session,
  tasks,
  employees
});
