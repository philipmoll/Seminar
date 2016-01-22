compositiondata:


compositiondata2:

compositiondata3:

timetabledata:

trackdata:
make sure the indices of the tracks in trackdata and networkdata coincide!
rows: tracks
columns: label1,label2,tracklength,parking,inspecting,cleaning,repairing,washing,type
values:	label: number of track
	label2: letter of track (if applicable)
	tracklength: length of track
	parking: 1 if parking allowed, -1 if preferably no parking, 0 if parking not allowed
	inspecting: 1 if inspection possible, 0 if no inspection possible
	cleaning: 1 if cleaning possible, 0 if no cleaning possible
	reparing: 1 if repairing possible, 0 if no repairing possible
	washing: 1 if washing possible, 0 if no washing possible
	type: 

networkdata:
make sure the indices of the tracks in trackdata and networkdata coincide!
rows: tracks
columns: tracks
values: 1 if direct connection between tracks, 0 if no direct connection between tracks