import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import projectReducer from "./projectReducer";

// Redux 提供了一个combineReducers方法，用于 Reducer 的拆分。
// 只要定义各个子 Reducer 函数，然后用这个方法，将它们合成一个大的 Reducer。

export default combineReducers({
  errors: errorReducer,
  project: projectReducer,
});
