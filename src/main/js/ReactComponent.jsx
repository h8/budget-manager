import React, {Component} from "react";
import PropTypes from "prop-types";
import {connect} from 'react-redux';
import {itemsFetchData} from './actions/items';
import {ListGroup, ListGroupItem} from 'react-bootstrap';

class ReactComponent extends Component {

  componentDidMount() {
    this.props.fetchData();
  }

  render() {
    if (this.props.hasError) {
      return <p>Sorry! There was an error loading the items</p>;
    }

    if (this.props.isLoading) {
      return <p>Loading…</p>;
    }

    return (
      <div style={setMargin}>
        {this.props.items.map((item) => (
          <div key={item.id}>
            <ListGroup style={setDistanceBetweenItems}>
              <ListGroupItem header={item.title}/>
            </ListGroup>
          </div>
        ))}
      </div>
    );
  }
}

let setMargin = {
  padding: "0px 200px 20px 200px"
};

let setDistanceBetweenItems = {
  marginBottom: "5px"
};

ReactComponent.propTypes = {
  fetchData: PropTypes.func.isRequired,
  items: PropTypes.array.isRequired,
  hasError: PropTypes.bool.isRequired,
  isLoading: PropTypes.bool.isRequired
};

const mapStateToProps = (state) => {
  return {
    items: state.items,
    hasError: state.itemsHaveError,
    isLoading: state.itemsAreLoading
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    fetchData: () => dispatch(itemsFetchData())
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(ReactComponent);