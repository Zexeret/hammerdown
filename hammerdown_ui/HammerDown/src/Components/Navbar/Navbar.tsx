import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { Link, useNavigate } from 'react-router-dom';
import { Avatar } from '@mui/material';
import { useUser } from '../../Context/UserProvider';
import axiosClient from '../../utils/axiosClient';

const Navbar: React.FC = () => {
  const { user, setUser } = useUser();
  const navigate = useNavigate();
 
  const handleSignOut = () => {
    setUser(null);
    navigator.credentials.preventSilentAccess();
    localStorage.removeItem('authToken');
    axiosClient.post('/auth/signout');
    navigate('/login');
    console.log('User signed out');
  };

  return (
    <AppBar position="static">
      <Toolbar>
      <Button color="inherit" component={Link} to="/explore">
          Products
        </Button>
        {user && (
          <Button color="inherit" component={Link} to="/myProducts">
            My Products
          </Button>
        )}
        <Typography variant="h6" style={{ flexGrow: 1, textAlign: 'center' }}>
          HammerDown
        </Typography>
        {user ? (
          <>
            <Button color="inherit" component={Link} to="/profile">
              <Avatar alt="Profile" src="profile-pic-url" style={{ width: 30, height: 30 }} />
            </Button>
            <Button color="inherit" onClick={handleSignOut}>
              Sign Out
            </Button>
          </>
        ) : (
          <>
            <Button color="inherit" component={Link} to="/login">
              Login
            </Button>
            <Button color="inherit" component={Link} to="/signUp">
              Sign Up
            </Button>
          </>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;