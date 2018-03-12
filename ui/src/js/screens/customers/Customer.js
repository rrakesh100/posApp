import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import Button from 'grommet/components/Button';
import FormFields from 'grommet/components/FormFields';
import FormField from 'grommet/components/FormField';
import Form from 'grommet/components/Form';
import Footer from 'grommet/components/Footer';
import Toast from 'grommet/components/Toast';
import TextInput from 'grommet/components/TextInput';
import {
  createCustomer, getCustomer, editCustomer, deleteCustomer
} from '../../actions/customers';


class Customer extends React.Component {

  constructor(){
    super();
    this.defaultState = {
      id: 0,
      name: '',
      dateOfBirth: '',
      mobileNumber:'',
      email: '',
      address: '',
      companyName: ''
    };
    this.state = {
      editFlow : false,
      id:0
    };
    this.onInputEntered = this.onInputEntered.bind(this);
    this.onSubmitCustomer=this.onSubmitCustomer.bind(this);

  }

  componentDidMount(){
    const { match, dispatch } = this.props;
    let customerId
    if(match)
      customerId = match.params.id
    else
      customerId = this.props.customerId;

      if(customerId != 0){
        this.setState({
          editFlow: true,
          id: customerId
        })
        getCustomer(customerId).then((response) => {
          const presentState = this.state;
          const newState = {...presentState , ...response.data}
        this.setState(newState);
        })
        .catch((error) => {
          console.log(error);
        });
      }else{
        this.setState({...this.defaultState});
      }
  }

  componentWillUnmount(){
    this.setState({});
  }


  componentWillReceiveProps(nextProps) {
    const { match } = nextProps;
    let customerId;
    if(match)
      customerId=match.params.id;
    else
      customerId = nextProps.customerId;

    if(customerId != 0){
      this.setState({
        editFlow: true,
        id: customerId
      })
      getCustomer(customerId).then((response) => {
      this.setState({...response.data});
      })
      .catch((error) => {
        console.log(error);
      });
    }else{
      this.setState({...this.defaultState});
    }
  }

  onInputEntered(fieldName, e) {
    this.setState({
      [fieldName]: e.target.value
    });
  }

  onSubmitCustomer(){
    const { id, name, dateOfBirth, mobileNumber, email, address, companyName } = this.state;
    const data = {
      id,
      name,
      dateOfBirth,
      mobileNumber,
      email,
      address,
      companyName
    }
    if(id == 0) {
    let result = createCustomer(data);
    result.then((response) => {
      this.props.onSubmit('add' , true , data.name )
    })
    .catch((error) => {
      this.props.onSubmit('add' , false , data.name );
    });
  }else {
    let result = editCustomer(data);
    result.then((response) => {
    this.props.onSubmit('edit' , true , data.name );
    })
    .catch( (error) => {
    this.props.onSubmit('edit' , false , data.name );
    });
  }
  this.setState({});
  }


  render() {
      return (
        <Form>
          <FormField label="Enter name of the customer" >
            <TextInput id='customerName'
              name='customerName'  onDOMChange={this.onInputEntered.bind(this, 'name' )}
              value={this.state.name}
            />
            </FormField>
          <FormField label="Date Of Birth">
            <TextInput id='customerDob'
              name='dateOfBirth'
              onDOMChange={this.onInputEntered.bind(this, 'dateOfBirth' )}
              value={this.state.dateOfBirth}
            />
            </FormField>
            <FormField label="Mobile Number">
              <TextInput id='customerMobile'
                name='mobileNumber'
                onDOMChange={this.onInputEntered.bind(this, 'mobileNumber' )}
                value={this.state.mobileNumber}
              />
              </FormField>

          <FormField label="Email">
            <TextInput id='customerEmail'
            name='email'
            onDOMChange={this.onInputEntered.bind(this, 'email' )}
            value={this.state.email}
          />
          </FormField>

          <FormField label="Address">
            <TextInput id='customerAddress'
            name='address'
            onDOMChange={this.onInputEntered.bind(this, 'address' )}
            value={this.state.address}
          />
          </FormField>
          <FormField label="Company Name">
            <TextInput id='companyName'
            name='companyName'
            onDOMChange={this.onInputEntered.bind(this, 'companyName' )}
            value={this.state.companyName}
          />
          </FormField>

      <Footer pad={{"vertical": "medium"}}>
        <Button label='Submit'
        primary={true}
        onClick={this.onSubmitCustomer.bind(this)}
        />
      </Footer>
      </Form>

      )
    }
  }


export default connect()(Customer);
