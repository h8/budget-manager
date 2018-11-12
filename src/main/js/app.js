import ReactComponent from './ReactComponent.jsx';
import React from 'react';
import {render} from 'react-dom';

let message = "Hello from JS!";

alert(message);

function plus(a, b) {
  return a + b;
}

export {
  plus
};

render ((<ReactComponent />), document.getElementById('container'));