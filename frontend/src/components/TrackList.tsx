import { Track } from '../lib/supabase';
import { Music } from 'lucide-react';

interface TrackListProps {
  tracks: Track[];
  currentTrackId: string | null;
  onTrackSelect: (track: Track) => void;
}

function formatDuration(seconds: number): string {
  const mins = Math.floor(seconds / 60);
  const secs = seconds % 60;
  return `${mins}:${secs.toString().padStart(2, '0')}`;
}

export default function TrackList({ tracks, currentTrackId, onTrackSelect }: TrackListProps) {
  return (
    <div className="bg-white rounded-2xl shadow-xl overflow-hidden">
      <div className="bg-gradient-to-r from-blue-600 to-blue-500 text-white p-6">
        <h3 className="text-2xl font-bold flex items-center gap-3">
          <Music size={28} />
          Playlist
        </h3>
        <p className="text-blue-100 mt-1">{tracks.length} músicas</p>
      </div>

      <div className="max-h-[500px] overflow-y-auto">
        {tracks.map((track, index) => (
          <div
            key={track.id}
            onClick={() => onTrackSelect(track)}
            className={`p-4 border-b border-gray-100 cursor-pointer transition-all duration-200 ${
              currentTrackId === track.id
                ? 'bg-blue-50 border-l-4 border-l-blue-600'
                : 'hover:bg-gray-50'
            }`}
          >
            <div className="flex items-center justify-between">
              <div className="flex items-center gap-4 flex-1">
                <span className="text-gray-400 font-semibold w-8 text-center">
                  {index + 1}
                </span>
                <div className="flex-1">
                  <h4
                    className={`font-semibold ${
                      currentTrackId === track.id ? 'text-blue-600' : 'text-gray-900'
                    }`}
                  >
                    {track.title}
                  </h4>
                  <p className="text-sm text-gray-500">{track.artist}</p>
                </div>
              </div>
              <div className="text-right">
                <p className="text-sm text-gray-400">{track.album}</p>
                <p className="text-xs text-gray-500 mt-1">{formatDuration(track.duration)}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
