import { Link } from 'react-router-dom';
import ResumeUploadPage from '../resume/ResumeUploadPage';

export default function DashboardPage() {
  return (
    <div className="min-h-screen bg-slate-950 text-slate-100">
      <header className="flex items-center justify-between px-8 py-6 border-b border-slate-800">
        <h1 className="text-2xl font-semibold">Resume Matching Dashboard</h1>
        <nav className="flex gap-4 text-sm">
          <Link className="text-slate-300 hover:text-white" to="/history">History</Link>
          <Link className="text-slate-300 hover:text-white" to="/admin">Admin</Link>
        </nav>
      </header>
      <main className="px-8 py-8 grid grid-cols-1 lg:grid-cols-3 gap-6">
        <section className="lg:col-span-2 space-y-6">
          <ResumeUploadPage />
          <div className="bg-slate-900 rounded-xl p-6 shadow">
            <h2 className="text-xl font-semibold mb-2">Matching Insights</h2>
            <p className="text-slate-300 text-sm">
              Run a match against a role description to see alignment scores and keyword overlap.
            </p>
          </div>
        </section>
        <aside className="bg-slate-900 rounded-xl p-6 shadow">
          <h2 className="text-xl font-semibold mb-3">Quick Actions</h2>
          <ul className="space-y-2 text-sm text-slate-300">
            <li>Review latest uploads</li>
            <li>Monitor audit events</li>
            <li>Manage team access</li>
          </ul>
        </aside>
      </main>
    </div>
  );
}
