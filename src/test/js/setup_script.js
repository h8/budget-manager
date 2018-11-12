import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

window.alert = jest.fn();

document.getElementById = (elementId) => document.createElement(elementId);

afterEach(() => {
  window.alert.mockReset();
});

Enzyme.configure({adapter: new Adapter()});