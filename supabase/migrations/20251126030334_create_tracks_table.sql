/*
  # Create tracks table for music player

  1. New Tables
    - `tracks`
      - `id` (uuid, primary key) - Unique identifier for each track
      - `title` (text) - Track title
      - `artist` (text) - Artist name
      - `album` (text) - Album name
      - `duration` (integer) - Duration in seconds
      - `created_at` (timestamptz) - Timestamp of creation

  2. Security
    - Enable RLS on `tracks` table
    - Add policy for public read access to tracks
*/

CREATE TABLE IF NOT EXISTS tracks (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  title text NOT NULL,
  artist text NOT NULL,
  album text NOT NULL DEFAULT '',
  duration integer NOT NULL DEFAULT 180,
  created_at timestamptz DEFAULT now()
);

ALTER TABLE tracks ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Anyone can view tracks"
  ON tracks
  FOR SELECT
  TO public
  USING (true);