import React, { Component } from 'react';
import './App.css';

class App extends Component {
  state = {
    posts: [],
  };

  componentDidMount() {
    console.log('componentDidMount');
    fetch('https://jsonplaceholder.typicode.com/posts', {
      // mode: 'no-cors',
    })
      .then(function(response) {
        return response.json();
      })
      .then(posts => {
        console.log('posts: ' + posts.length);
        this.setState({ 
          posts: posts
        })
      })
      .catch((e) => {
        console.log('Error: ' + e);
      });

  }

  render() {
    const { posts } = this.state;
    return (
      <div className="App">
        <header className="App-header">
          My Blog        
        </header>
        <main className="App-main">
          Posts:
          {posts.map(post => (
            <section key={post.id}>
              <h2>{post.title}</h2>
              <p>{post.body}</p>
            </section>
          ))}
        </main>
      </div>
    );
  }
}

export default App;
