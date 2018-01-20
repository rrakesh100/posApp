import React from 'react';
import Button from 'grommet/components/Button';



class Customer extends React.Component {


  constructor(){
    super();
    this.handleData=this.handleData.bind(this);
    this.sendMessage=this.sendMessage.bind(this);
    this.stopConnection = this.stopConnection.bind(this);
    this.state = {
      messages : []
    }

  }

  handleData(data){
    console.log(data);
  }

  sendMessage() {
    console.log('button clicked');
    const { connection } = this.state;
    setInterval( _ =>{
         connection.send(JSON.stringify( { name : 'name - ' + Math.random() ,
                       value : 'value - ' + Math.random()}));
     }, 10000 )

  }

 stopConnection() {
   const { connection } = this.state;
   connection.close();
 }

  componentDidMount(){
   let connection = new WebSocket('ws://localhost:8080/name');
    connection.onmessage = (evt) => {
     // add the new message to state
       this.setState({
       messages : [...this.state.messages  ,  evt.data ]
     })
   };

   this.setState({
     connection
   })

  }

  render() {
  //  return (<h1>hi</h1>)
  const { messages } = this.state ;

  console.log(messages);

  return (
    <div>
            <ul>
             { messages.map((message) => {
               return <li>{message}</li>
             })
            }
             </ul>
            <Button
              label='Send'
              onClick={this.sendMessage.bind(this)}
              primary={true}  />

              <Button
                label='stop'
                onClick={this.stopConnection.bind(this)}
                primary={true}  />



      </div>
    )
  }

}

export default Customer
