jest.mock('react-dom', function () {
  return {
    render: jest.fn()
  };
});

it('creates store, renders root component and starts app', function() {
  const mountPoint = {id: 'container'};
  const getMountPoint = jest.spyOn(global.document, 'getElementById').mockImplementation(() => mountPoint);
  require('../../main/js/app');

  const ReactDOM = require('react-dom');
  expect(getMountPoint).toHaveBeenCalledWith('container');
  expect(ReactDOM.render).toHaveBeenCalledWith(expect.anything(), mountPoint);
});
