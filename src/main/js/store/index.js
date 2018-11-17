import {applyMiddleware, createStore} from "redux";
import thunk from "redux-thunk";
import rootReducer from "../reducers";

export default function configureStore(initialState) {
  const enhancer = applyMiddleware(thunk);

  return createStore(
    rootReducer,
    initialState,
    enhancer
  );
}
