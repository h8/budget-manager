import React from 'react';
import { shallow } from 'enzyme';

import ReactComponent from "../../main/js/ReactComponent.jsx";

describe('ReactComponent', () => {
  it('should render correctly', () => {

    const component = shallow(<ReactComponent />);

    expect(component).toBeDefined();
  });
});