jest.mock('../../main/js/store', function () {
  const {mockStore} = require('./store_mock');
  let store = mockStore({});
  let createStore = jest.fn(() => store);
  createStore.default = createStore;
  return createStore;
});

jest.mock('react-dom', function () {
  const render = jest.fn();
  return {
    default: { render },
    render
  };
});

it('creates store, renders root component and starts app', function() {
  const mountPoint = {id: 'container'};
  const getMountPoint = jest.spyOn(global.document, 'getElementById').mockImplementation(() => mountPoint);
  require('../../main/js/app');

  const configureStore = require('../../main/js/store');
  const ReactDOM = require('react-dom');
  expect(getMountPoint).toHaveBeenCalledWith('container');
  expect(configureStore).toHaveBeenCalled();
  expect(ReactDOM.render).toHaveBeenCalledWith(expect.anything(), mountPoint);
});
