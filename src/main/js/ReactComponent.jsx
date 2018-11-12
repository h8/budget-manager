import React, { Component } from "react";

class ReactComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      message: "React rendered"
    };
  }

  render() {
    const { message } = this.state;
    return (
      <h1>{message}</h1>
    );
  }
}

export default ReactComponent;