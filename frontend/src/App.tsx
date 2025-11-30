import { useState, useEffect } from 'react';
import { Track } from './lib/supabase';
import MusicPlayer from './components/MusicPlayer';
import TrackList from './components/TrackList';

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;

type BackendMusica = {
  id: number;
  titulo: string;
  artista: string;
  album: string;
  nomeArquivo: string;
};

function App() {
  const [tracks, setTracks] = useState<Track[]>([]);
  const [currentTrackIndex, setCurrentTrackIndex] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchTracks();
  }, []);

  async function fetchTracks() {
    try {
      const res = await fetch(`${BACKEND_URL}/api/playlist`);
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const data: BackendMusica[] = await res.json();

      // Map backend Musica -> UI Track
      const mapped: Track[] = data.map((m) => ({
        id: String(m.id),
        title: m.titulo,
        artist: m.artista,
        album: m.album,
        duration: 180, // default duration for UI
        created_at: new Date().toISOString(),
      }));

      setTracks(mapped);
    } catch (error) {
      console.error('Error fetching tracks from backend:', error);
    } finally {
      setLoading(false);
    }
  }

  const handlePlayPause = () => {
    setIsPlaying(!isPlaying);
  };

  const handleNext = () => {
    if (tracks.length === 0) return;
    setCurrentTrackIndex((prev) => (prev + 1) % tracks.length);
  };

  const handlePrevious = () => {
    if (tracks.length === 0) return;
    setCurrentTrackIndex((prev) => (prev - 1 + tracks.length) % tracks.length);
  };

  const handleTrackSelect = (track: Track) => {
    const index = tracks.findIndex((t) => t.id === track.id);
    if (index !== -1) {
      setCurrentTrackIndex(index);
      setIsPlaying(true);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-16 w-16 border-b-4 border-blue-600 mx-auto"></div>
          <p className="mt-4 text-blue-800 font-semibold">Carregando músicas...</p>
        </div>
      </div>
    );
  }

  const currentTrack = tracks[currentTrackIndex] || null;

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-blue-100 py-8 px-4">
      <div className="max-w-7xl mx-auto">
        <header className="mb-8 text-center">
          <h1 className="text-5xl font-bold text-blue-900 mb-2">Music Player</h1>
          <p className="text-blue-700">Sua música, seu momento</p>
        </header>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          <div>
            <MusicPlayer
              currentTrack={currentTrack}
              isPlaying={isPlaying}
              onPlayPause={handlePlayPause}
              onNext={handleNext}
              onPrevious={handlePrevious}
            />
          </div>

          <div>
            <TrackList
              tracks={tracks}
              currentTrackId={currentTrack?.id || null}
              onTrackSelect={handleTrackSelect}
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
