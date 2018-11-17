import ReactComponent from './ReactComponent.jsx';
import React from 'react';
import {render} from 'react-dom';
import { Provider } from 'react-redux';
import configureStore from './store';

const store = configureStore();

render (
  <Provider store={store}>
    <ReactComponent />
  </Provider>,
  document.getElementById('container')
);