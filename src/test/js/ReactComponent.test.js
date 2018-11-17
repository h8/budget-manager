import React from 'react';
import { shallow } from 'enzyme';
import { Provider } from 'react-redux';

import ReactComponent from "../../main/js/ReactComponent.jsx";

describe('ReactComponent', () => {
  it('should render correctly', () => {

    const component = shallow(<Provider><ReactComponent /></Provider>);

    expect(component).toBeDefined();
  });
});