import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { login } from './authApi';
import { setCredentials } from './authSlice';

export default function LoginPage() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    setError(null);
    try {
      const response = await login(email, password);
      dispatch(setCredentials({ ...response, email }));
      navigate('/dashboard');
    } catch (err) {
      setError('Login failed. Check your credentials.');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-950 text-slate-100">
      <div className="w-full max-w-md bg-slate-900 p-8 rounded-xl shadow-lg">
        <h1 className="text-2xl font-semibold mb-6">Sign in</h1>
        {error ? <p className="text-red-400 mb-4">{error}</p> : null}
        <form className="space-y-4" onSubmit={handleSubmit}>
          <div>
            <label className="block text-sm mb-1">Email</label>
            <input
              className="w-full px-3 py-2 rounded-md bg-slate-800 border border-slate-700"
              type="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              required
            />
          </div>
          <div>
            <label className="block text-sm mb-1">Password</label>
            <input
              className="w-full px-3 py-2 rounded-md bg-slate-800 border border-slate-700"
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              required
            />
          </div>
          <button className="w-full bg-indigo-500 hover:bg-indigo-400 transition px-4 py-2 rounded-md" type="submit">
            Login
          </button>
        </form>
        <p className="text-sm text-slate-400 mt-4">
          New here? <a className="text-indigo-400" href="/register">Create an account</a>
        </p>
      </div>
    </div>
  );
}
