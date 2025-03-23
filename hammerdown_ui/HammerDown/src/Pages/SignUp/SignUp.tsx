import React, { useState } from 'react';
import { Button, TextField, Container, Typography } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import axiosClient from '../../utils/axiosClient';

type Props = {};

const SignUp: React.FC<Props> = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSignUp = async (username: string, email: string, password: string): Promise<number> => {
    try {
        const response = await axiosClient.post('/auth/signup', {
            username,
            email,
            password,
        });

        if (response.status === 200) {
            console.log('Sign Up successful:', response.data);
            navigate('/login');
        } else {
            console.error('Sign Up failed');
        }
        return response.status;
    } catch (error) {
        console.error('An error occurred during Sign Up:', error);
        return 500;
    }
};

  const onSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    const status = await handleSignUp(username, email, password); 
    
    if (status !== 200) {
      console.error('Sign Up failed with status:', status);

    }
  };

  return (
    <Container maxWidth="sm">
      <br />
      <Typography variant="h4" component="h1" gutterBottom textAlign={'center'}>
        Sign Up
      </Typography>
      <form autoComplete="off" onSubmit={onSubmit}>
        <TextField
          label="User Name"
          variant="outlined"
          margin="normal"
          required
          fullWidth
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <TextField
          label="Email"
          type="email"
          variant="outlined"
          margin="normal"
          required
          fullWidth
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <TextField
          label="Password"
          type="password"
          variant="outlined"
          margin="normal"
          required
          fullWidth
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <TextField
          label="Confirm Password"
          type="password"
          variant="outlined"
          margin="normal"
          required
          fullWidth
        />
        <TextField
          label="Contact Number"
          type="number"
          variant="outlined"
          margin="normal"
          required
          fullWidth
        />
        <TextField
          label="Address"
          type="text"
          variant="outlined"
          margin="normal"
          required
          fullWidth
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          color="primary"
          style={{ marginTop: '10px' }}
        >
          Sign Up
        </Button>
      </form>
      <Typography variant="body1" style={{ marginTop: '1rem' }}>
        Already have an account? <Link to="/login">Login</Link>
      </Typography>
    </Container>
  );
};

export default SignUp;