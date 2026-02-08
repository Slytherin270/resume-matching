import { useQuery } from '@tanstack/react-query';
import api from '../../lib/api';

interface AuditEntry {
  id: string;
  action: string;
  details: string;
  createdAt: string;
}

export default function HistoryPage() {
  const { data, isLoading } = useQuery({
    queryKey: ['audit'],
    queryFn: async () => {
      const response = await api.get<{ content: AuditEntry[] }>('/audit');
      return response.data.content;
    },
  });

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 px-8 py-8">
      <h1 className="text-2xl font-semibold mb-6">Audit History</h1>
      <div className="bg-slate-900 rounded-xl p-6 shadow">
        {isLoading ? <p>Loading...</p> : null}
        <ul className="space-y-3">
          {data?.map((entry) => (
            <li key={entry.id} className="border-b border-slate-800 pb-3">
              <p className="text-sm text-slate-300">{entry.action}</p>
              <p className="text-sm">{entry.details}</p>
              <p className="text-xs text-slate-500">{new Date(entry.createdAt).toLocaleString()}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}
