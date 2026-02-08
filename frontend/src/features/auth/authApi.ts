import api from '../../lib/api';

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
}

export async function login(email: string, password: string): Promise<AuthResponse> {
  const { data } = await api.post<AuthResponse>('/auth/login', { email, password });
  return data;
}

export async function register(email: string, password: string): Promise<AuthResponse> {
  const { data } = await api.post<AuthResponse>('/auth/register', { email, password });
  return data;
}
