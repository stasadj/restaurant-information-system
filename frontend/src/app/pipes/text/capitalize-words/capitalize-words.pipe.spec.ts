import { CapitalizeWordsPipe } from './capitalize-words.pipe';

describe('CapitalizeWordsPipe', () => {
  it('create an instance', () => {
    const pipe = new CapitalizeWordsPipe();
    expect(pipe).toBeTruthy();
  });
});
