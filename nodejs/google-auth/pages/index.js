import Head from 'next/head';

function Home() {
  return (
    <div>
      <Head>
        <title>My page title</title>
        <meta name="viewport" content="initial-scale=1.0, width=device-width" />
      </Head>
      <p>Hello world!</p>
      <div>
        <a href="/auth/user">User</a>
        &nbsp;&nbsp;
        <a href="/auth/user/profile">Profile</a>
        &nbsp;&nbsp;
        <a href="/auth/logout">Logout</a>
      </div>
    </div>
  );
}

export default Home;