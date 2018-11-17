jest.mock('../../main/js/store', function () {
  const {mockStore} = require('./store_mock');
  let store = mockStore({});
  return {
    default: jest.fn(() => store),
  };
});

jest.mock('react-dom', function () {
  return {
    render: jest.fn()
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
