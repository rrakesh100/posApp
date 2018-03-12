import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import Button from 'grommet/components/Button';
import FormFields from 'grommet/components/FormFields';
import FormField from 'grommet/components/FormField';
import Form from 'grommet/components/Form';
import Footer from 'grommet/components/Footer';
import Toast from 'grommet/components/Toast';
import TextInput from 'grommet/components/TextInput';
import { createSupplier, getSupplier, editSupplier, deleteSupplier } from '../../actions/suppliers'

class Supplier extends React.Component {
  constructor() {
    super();
    this.defaultState = {
      id : 0,
      mobileNumber : '',
      companyName : '',
      agencyName : ''
    }
    this.state = {
      editFlow : false,
      id : 0
    };
    this.onInputEntered = this.onInputEntered.bind(this);
    this.onSubmitSupplier=this.onSubmitSupplier.bind(this);

  }

  componentDidMount() {
    const { match, dispatch } = this.props
    let supplierId
    if(match) {
      supplierId = match.params.id;
    } else {
       supplierId = this.props.supplierId;
    }
    if(supplierId != 0) {
      this.setState({
        editFlow : true,
        id : supplierId
      })
      getSupplier(supplierId).then((response) => {
        const presentState = this.state
        const newState = {...presentState, ...response.data}
        this.setState(newState);
      }).catch((error) => {
        console.log(error)
      })
    } else {
      this.setState({...this.defaultState})
    }
  }
componentWillUnmount() {
  this.setState({})
}
  componentWillReceiveProps(nextProps) {
    const { match } = nextProps;
    let supplierId;
    if(match)
      supplierId = match.params.id;
    else
      supplierId = nextProps.supplierId;

    if(supplierId != 0){
      this.setState({
        editFlow: true,
        id: supplierId
      })
      getSupplier(supplierId).then((response) => {
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
      [ fieldName ] : e.target.value
    })
}

onSubmitSupplier() {
  const { id, mobileNumber, companyName, agencyName } = this.state
  const data = {
    id,
    mobileNumber,
    companyName,
    agencyName
  }
  if(id == 0) {
  let result = createSupplier(data)
  result.then((response) => {
    this.props.onSubmit('add', true, data.name)
  }).catch((error) => {
    this.props.onSubmit('add', false, data.name)
  })
} else {
  let result = editSupplier(data)
  result.then((response) => {
    this.props.onSubmit('edit', true, data.name)
  }).catch((error) => {
    this.props.onSubmit('edit', false, data.name)
  })
}
this.setState({})
}

  render() {
    return (
      <Form>
      <FormField label="Enter the company's name">
        <TextInput id="companyName"
        name="companyName" onDOMChange={this.onInputEntered.bind(this, 'companyName')}
        value={this.state.companyName}
        />
      </FormField>
        <FormField label="Mobile Number">
          <TextInput id="mobileNumber"
          name="mobileNumber" onDOMChange={this.onInputEntered.bind(this, 'mobileNumber')}
          value={this.state.mobileNumber}
          />
        </FormField>
        <FormField label="Agency Name">
          <TextInput id="agencyName"
          name="agencyName" onDOMChange={this.onInputEntered.bind(this, 'agencyName')}
          value={this.state.agencyName}
          />
        </FormField>
        <Footer pad={{"vertical" : "medium"}}>
        <Button
        label="Submit"
        primary={true} onClick={this.onSubmitSupplier.bind(this)}
        />
        </Footer>
      </Form>
    );
  }
}

export default Supplier
