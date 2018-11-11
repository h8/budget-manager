window.alert = jest.fn();

afterEach(() => {
  window.alert.mockReset();
});
