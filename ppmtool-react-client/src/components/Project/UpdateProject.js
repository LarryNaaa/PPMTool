import React, { Component } from "react";
import { getProject, createProject } from "../../actions/projectActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";

class UpdateProject extends Component {
  //set state
  constructor() {
    super();

    // take the attribute of the project
    this.state = {
      id: "",
      projectName: "",
      projectIdentifier: "",
      description: "",
      start_date: "",
      end_date: "",
      errors: {},
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  // lifecycle hook
  // 已加载组件收到新的参数时调用
  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }

    const {
      id,
      projectName,
      projectIdentifier,
      description,
      start_date,
      end_date,
    } = nextProps.project;

    this.setState({
      id,
      projectName,
      projectIdentifier,
      description,
      start_date,
      end_date,
    });
  }

  // Mounting：已插入真实 DOM
  // Updating：正在被重新渲染
  // Unmounting：已移出真实 DOM
  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getProject(id, this.props.history);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();

    const updateProject = {
      id: this.state.id,
      projectName: this.state.projectName,
      projectIdentifier: this.state.projectIdentifier,
      description: this.state.description,
      start_date: this.state.start_date,
      end_date: this.state.end_date,
    };

    this.props.createProject(updateProject, this.props.history);
  }

  render() {
    const { errors } = this.state;

    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Update Project form</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.projectName,
                    })}
                    placeholder="Project Name"
                    name="projectName"
                    value={this.state.projectName}
                    onChange={this.onChange}
                  />
                  {errors.projectName && (
                    <div className="invalid-feedback">{errors.projectName}</div>
                  )}
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Unique Project ID"
                    name="projectIdentifier"
                    value={this.state.projectIdentifier}
                    onChange={this.onChange}
                    disabled
                  />
                </div>
                <div className="form-group">
                  <textarea
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.description,
                    })}
                    placeholder="Project Description"
                    name="description"
                    onChange={this.onChange}
                    value={this.state.description}
                  />
                  {errors.description && (
                    <div className="invalid-feedback">{errors.description}</div>
                  )}
                </div>
                <h6>Start Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="start_date"
                    value={this.state.start_date}
                    onChange={this.onChange}
                  />
                </div>
                <h6>Estimated End Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="end_date"
                    value={this.state.end_date}
                    onChange={this.onChange}
                  />
                </div>

                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

// 组件类的PropTypes属性，用来验证组件实例的属性是否符合要求
// 组件的属性可以接受任意值，字符串、对象、函数等等都可以。
// 有时，我们需要一种机制，验证别人使用组件时，提供的参数是否符合要求。
UpdateProject.propTypes = {
  getProject: PropTypes.func.isRequired,
  createProject: PropTypes.func.isRequired,
  project: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
};

// mapStateToProps是一个函数。它的作用就是像它的名字那样，
// 建立一个从（外部的）state对象到（UI 组件的）props对象的映射关系。
const mapStateToProps = (state) => ({
  project: state.project.project,
  errors: state.errors,
});

// React-Redux 提供connect方法，用于从 UI 组件生成容器组件。
// connect的意思，就是将这两种组件连起来。
// connect方法接受两个参数：mapStateToProps和mapDispatchToProps。
// 它们定义了 UI 组件的业务逻辑。
// 前者负责输入逻辑，即将state映射到 UI 组件的参数（props），
// 后者负责输出逻辑，即将用户对 UI 组件的操作映射成 Action。

// mapDispatchToProps是connect函数的第二个参数，
// 用来建立 UI 组件的参数到store.dispatch方法的映射。
// 也就是说，它定义了哪些用户的操作应该当作 Action，传给 Store。
// 它可以是一个函数，也可以是一个对象。

export default connect(mapStateToProps, { getProject, createProject })(
  UpdateProject
);
