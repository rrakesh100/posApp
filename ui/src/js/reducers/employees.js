import { createReducer } from './utils';
import { EMPLOYEES_LOAD, EMPLOYEES_UNLOAD, EMPLOYEES_ADD} from '../actions';


const initialState = {
  employees: []
};

const handlers = {
  [EMPLOYEES_LOAD]: (state, action) => {
    return { employees: action.data };
  },
  [EMPLOYEES_ADD]: (state, action) => {
    console.log(state);
    console.log(action);
    return {...state, add : true};
  },
  [EMPLOYEES_UNLOAD]: () => initialState
};


export default createReducer(initialState, handlers);
