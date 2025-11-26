import { Play, Pause, SkipBack, SkipForward } from 'lucide-react';
import { Track } from '../lib/supabase';

interface MusicPlayerProps {
  currentTrack: Track | null;
  isPlaying: boolean;
  onPlayPause: () => void;
  onNext: () => void;
  onPrevious: () => void;
}

function formatDuration(seconds: number): string {
  const mins = Math.floor(seconds / 60);
  const secs = seconds % 60;
  return `${mins}:${secs.toString().padStart(2, '0')}`;
}

export default function MusicPlayer({
  currentTrack,
  isPlaying,
  onPlayPause,
  onNext,
  onPrevious,
}: MusicPlayerProps) {
  if (!currentTrack) {
    return null;
  }

  return (
    <div className="bg-gradient-to-r from-blue-900 to-blue-800 text-white p-8 rounded-2xl shadow-2xl">
      <div className="flex items-center justify-between mb-8">
        <div className="flex-1">
          <h2 className="text-3xl font-bold mb-2">{currentTrack.title}</h2>
          <p className="text-blue-200 text-lg">{currentTrack.artist}</p>
          <p className="text-blue-300 text-sm mt-1">{currentTrack.album}</p>
        </div>
        <div className="text-right">
          <p className="text-blue-200 text-sm">Duração</p>
          <p className="text-2xl font-semibold">{formatDuration(currentTrack.duration)}</p>
        </div>
      </div>

      <div className="flex items-center justify-center gap-6">
        <button
          onClick={onPrevious}
          className="p-4 bg-blue-700 hover:bg-blue-600 rounded-full transition-all duration-200 hover:scale-110"
        >
          <SkipBack size={24} />
        </button>

        <button
          onClick={onPlayPause}
          className="p-6 bg-white text-blue-900 hover:bg-blue-50 rounded-full transition-all duration-200 hover:scale-110 shadow-lg"
        >
          {isPlaying ? <Pause size={32} /> : <Play size={32} className="ml-1" />}
        </button>

        <button
          onClick={onNext}
          className="p-4 bg-blue-700 hover:bg-blue-600 rounded-full transition-all duration-200 hover:scale-110"
        >
          <SkipForward size={24} />
        </button>
      </div>
    </div>
  );
}
