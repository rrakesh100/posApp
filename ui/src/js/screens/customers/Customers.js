import React from 'react';
import Button from 'grommet/components/Button';



class Customer extends React.Component {


  constructor(){
    super();
    this.handleData=this.handleData.bind(this);
    this.sendMessage=this.sendMessage.bind(this);
    this.stopConnection = this.stopConnection.bind(this);
    this.startSse=this.startSse.bind(this);
    this.stopSse=this.stopSse.bind(this);
    this.state = {
      messages : [],
      items : []
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
                       value :  Math.random()}));
     }, 5000 )

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
   });

  }


  startSse() {
    let itemsURL = 'http://localhost:8080/customers';
     let source;
     const { items } = this.state;
    if(!!window.EventSource) {
      source = new EventSource(itemsURL); const ref=this; let newarr=[];
       source.addEventListener('message', function(e) {
         const { items } = ref.state;
         let data = JSON.parse(e.data);
         console.log(data);
         if (data && data.length > 0) {
            newarr = [ ...items , ...data ];
            ref.setState({
              items : newarr
            });
         } else
           console.log("No items found");
       }, true);
       source.addEventListener('open', function(e) {
         console.log("Connection opened");
       }, true);
       source.addEventListener('error', function(e) {
         console.log("Connection error");
       }, true);
       source.addEventListener('close', function(e) {
         console.log("Connection closed");
       }, true);

       this.setState({
         eventSource : source
       })
    }
  }


  stopSse(){
    const { eventSource } = this.state;

    eventSource.close();
  }

  render() {
  //  return (<h1>hi</h1>)
  const { messages, items } = this.state ;

  console.log(messages);

  return (
    <div>
            <ul>
             { messages.map((message) => {
               return <li>{message}</li>
             })
            }
             </ul>
             <div>
            <Button
              label='start websocket'
              onClick={this.sendMessage.bind(this)}
              primary={true}  />

              <Button
                label='stop websocket'
                onClick={this.stopConnection.bind(this)}
                primary={true}  />
                </div>

                <h1>Server sent events start ...</h1>

                <div>

                <Button
                  label='start sse'
                  onClick={this.startSse.bind(this)}
                  primary={true}  />

                  <Button
                    label='stop sse'
                    onClick={this.stopSse.bind(this)}
                    primary={true}  />
                    </div>

                    <ul>
                     {
                        items.map((item) => {
                       return <li>{item.name}</li>
                     })
                    }
                     </ul>




      </div>
    )
  }

}

export default Customer
