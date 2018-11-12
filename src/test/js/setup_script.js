import Enzyme from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

window.alert = jest.fn();

afterEach(() => {
  window.alert.mockReset();
});

Enzyme.configure({adapter: new Adapter()});