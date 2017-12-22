import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import $ from 'jquery';

class App extends Component {

  constructor(props){
    super(props);
    this.state = {'items' : []};
  }


  componentDidMount(){
    $.ajax({
      url:'http://localhost:8080/v1/items',
      success: (data) => {
        this.setState({'items': data});
      }
    })
  }

  render() {
    console.log('render called');
    const { items } = this.state;
    console.log(items);
    return (
      <div className="App">
        <ul>
          {
            items.map((item) => {
              return <li key={item.id}>
                {item.name} ---- {item.description}
              </li>
            })
          }
        </ul>

      </div>
    );
  }
}

export default App;
