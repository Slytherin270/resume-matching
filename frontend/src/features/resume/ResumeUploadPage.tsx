import { useState } from 'react';
import api from '../../lib/api';

export default function ResumeUploadPage() {
  const [file, setFile] = useState<File | null>(null);
  const [message, setMessage] = useState<string | null>(null);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    if (!file) {
      setMessage('Please select a file.');
      return;
    }
    const formData = new FormData();
    formData.append('file', file);
    try {
      await api.post('/resumes/upload', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      setMessage('Resume uploaded successfully.');
    } catch (error) {
      setMessage('Upload failed.');
    }
  };

  return (
    <div className="bg-slate-900 rounded-xl p-6 shadow">
      <h2 className="text-xl font-semibold mb-4">Upload Resume</h2>
      {message ? <p className="text-sm text-slate-300 mb-3">{message}</p> : null}
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          className="block w-full text-sm text-slate-200 file:mr-4 file:py-2 file:px-4 file:rounded-md file:border-0 file:bg-indigo-500 file:text-white"
          type="file"
          onChange={(event) => setFile(event.target.files?.[0] ?? null)}
          required
        />
        <button className="bg-indigo-500 hover:bg-indigo-400 transition px-4 py-2 rounded-md" type="submit">
          Upload
        </button>
      </form>
    </div>
  );
}
