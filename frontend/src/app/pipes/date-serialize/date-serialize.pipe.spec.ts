import { DateSerializePipe } from './date-serialize.pipe';

describe('DateSerializePipe', () => {
  it('create an instance', () => {
    const pipe = new DateSerializePipe();
    expect(pipe).toBeTruthy();
  });
});
