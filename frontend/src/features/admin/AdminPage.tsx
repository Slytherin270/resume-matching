export default function AdminPage() {
  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 px-8 py-8">
      <h1 className="text-2xl font-semibold mb-6">Admin Console</h1>
      <div className="bg-slate-900 rounded-xl p-6 shadow">
        <p className="text-slate-300 text-sm">
          Manage user access, audit exports, and system health from this dashboard.
        </p>
      </div>
    </div>
  );
}
