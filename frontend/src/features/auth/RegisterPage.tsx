import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { register } from './authApi';
import { setCredentials } from './authSlice';

export default function RegisterPage() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    setError(null);
    try {
      const response = await register(email, password);
      dispatch(setCredentials({ ...response, email }));
      navigate('/dashboard');
    } catch (err) {
      setError('Registration failed. Try a different email.');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-950 text-slate-100">
      <div className="w-full max-w-md bg-slate-900 p-8 rounded-xl shadow-lg">
        <h1 className="text-2xl font-semibold mb-6">Create account</h1>
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
          <button className="w-full bg-emerald-500 hover:bg-emerald-400 transition px-4 py-2 rounded-md" type="submit">
            Register
          </button>
        </form>
        <p className="text-sm text-slate-400 mt-4">
          Already have an account? <a className="text-indigo-400" href="/login">Sign in</a>
        </p>
      </div>
    </div>
  );
}
