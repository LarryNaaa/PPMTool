import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initalState = {};
const middleware = [thunk];

// Store 就是保存数据的地方，可以把它看成一个容器。整个应用只能有一个 Store。
let store;

if (window.navigator.userAgent.includes("Chrome")) {
  // 实际应用中，Reducer 函数不用像上面这样手动调用，
  // store.dispatch方法会触发 Reducer 的自动执行。
  // 为此，Store 需要知道 Reducer 函数，做法就是在生成 Store 的时候，
  // 将 Reducer 传入createStore方法。

  store = createStore(
    rootReducer,
    initalState,
    compose(
      applyMiddleware(...middleware),
      window.__REDUX_DEVTOOLS_EXTENSION__ &&
        window.__REDUX_DEVTOOLS_EXTENSION__()
    )
  );
} else {
  store = createStore(
    rootReducer,
    initalState,
    compose(applyMiddleware(...middleware))
  );
}

export default store;
